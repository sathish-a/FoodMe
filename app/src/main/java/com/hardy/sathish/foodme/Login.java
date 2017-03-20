package com.hardy.sathish.foodme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.util.ArrayList;


public class Login extends Activity implements View.OnClickListener {
    EditText EtCusName, EtPhNo, EtTableNo;
    Button enter;
    String CusName, PhNo;
    Integer TableNo;
    Integer CusId;
   //  String URL = "http://kewldevs.hol.es/login.php"; //Universal Link
    String URL = "http://192.168.0.2/food/login.php";
    int login_code = 0;
    String greetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EtCusName = (EditText) findViewById(R.id.cusName);
        EtCusName.setSingleLine();
        EtPhNo = (EditText) findViewById(R.id.PhNo);
        EtTableNo = (EditText) findViewById(R.id.TableNo);
        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.enter) {
            CusName = EtCusName.getText().toString();
            PhNo = EtPhNo.getText().toString();
            String rawTableNo = EtTableNo.getText().toString();
            if(!rawTableNo.isEmpty()) {
                TableNo = Integer.parseInt(rawTableNo);
                if (!CusName.isEmpty() && !PhNo.isEmpty() && TableNo != 0) {
                    Log.v("Inputs:", CusName + "," + PhNo + "," + TableNo);
                    Background bg = new Background();
                    bg.execute(URL);
                } else {
                   DialogBox();
                }
            }
            else DialogBox();
        }
    }

    void DialogBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Careful!!");
        alert.setMessage("Make sure you have filled all the fields");
        Dialog dialog = alert.create();
        dialog.show();
    }
    class Background extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(Login.this);

        InputStream inputStream = null;
        String result = "";

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading..");
            dialog.show();
        }


        @Override
        protected Void doInBackground(String... urls) {
            HttpEntity entity;

         for (String url : urls) {
                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("CusName", CusName));
                    pairs.add(new BasicNameValuePair("PhNo", PhNo));
                    pairs.add(new BasicNameValuePair("TableNo", "" + TableNo));
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);
                    entity = response.getEntity();
                    inputStream = entity.getContent();
                }catch (ProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    Toast.makeText(Login.this, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }


                try {
                    BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sBuilder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        sBuilder.append(line + "\n");
                        inputStream.close();
                        result = sBuilder.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                login_code = jsonObject.getInt("success");
                greetings = jsonObject.getString("message");
                CusId = jsonObject.getInt("CusId");
                Toast.makeText(Login.this, greetings, Toast.LENGTH_LONG).show();
                if (login_code == 1) {
                    dialog.dismiss();
                    Intent i = new Intent(Login.this, Menu.class);
                    i.putExtra("CustomerId",CusId);
                    startActivity(i);
                    finish();
                    Log.v("Customer Id:", ""+CusId);
                } else {
                    dialog.dismiss();
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


