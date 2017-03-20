package com.hardy.sathish.foodme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sathish on 08/08/2015.
 */
public class PostOrders extends AsyncTask<String, Void, Void> {
    List<Orders> newOrders;
    Context context;
    ProgressDialog dialog;
    Integer CustomerId;
    double total;
    public PostOrders(List<Orders> newOrders, Context context, Integer customerId, double total) {
        this.context = context;
        this.newOrders = newOrders;
        CustomerId = customerId;
        this.total = total;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Placing orders...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {

        Log.v("Status","Working fine");

        for( String url : params)
        {
            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                for(int i=0;i<newOrders.size();i++)
                {
                    Orders orders = newOrders.get(i);
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("CusId","" + CustomerId));
                    pairs.add(new BasicNameValuePair("FoodId","" + orders.foodId));
                    pairs.add(new BasicNameValuePair("Qty", "" + orders.getQty()));
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);
                    Log.v("Count",""+i);
                }
            }catch (IOException e) {
                Toast.makeText(context, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        Toast.makeText(context,"Orders Added Successfully!!",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context,Bill.class);
        i.putExtra("amount",total);
        context.startActivity(i);


    }
}
