package com.hardy.sathish.foodme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Menu extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    // String URL = "http://kewldevs.hol.es/menu.php";//Universal Link
    public String localhost = "http://192.168.0.2/food/"; // General URL Link

    /*List Items*/
    List<Food> foodList = new ArrayList<Food>();   // this list contains the items of menu
    List<Orders> ordersList = new ArrayList<Orders>(); // this list contains the order items
    List<Orders> newOrders = new ArrayList<Orders>();  // this is temp of order List

    /*List Views*/
    ListView listView, orderListView; // listView shows the list items present in foodList
                                      // orderListView show the list items present in ordersList
    /*Adapters*/
    FoodAdapter foodAdapter;         // Food Adapters is a class which manages the custom list views of foodList
    OrderAdapter orderAdapter;       //OrderAdapter is a class which manages the custom list views of orderList

    /*Buttons*/
    Button myOrders, placeOrders;     //myOrders Button add orders to ordersList,placeOrders post the OrdersList to DB

    Integer CustomerId;               //This Contains the CustomerId returned from server

    TextView totalCost;              // This text view displays the total cost of orders

    double total=0.0;                 // This contains the total cost of orders
    //  EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*** Why this strict mode is I'm retrieving the image from url in OnPostExecute() method */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /**This sets my menu layout*/

        setContentView(R.layout.activity_menu);

        CustomerId=getIntent().getExtras().getInt("CustomerId"); //This retrieves customer id from previous activity

        /**setting up list*/
        listView = (ListView) findViewById(R.id.list);
        orderListView = (ListView) findViewById(R.id.orderList);

        /***Setting up tabs***/
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("menu");
        tabSpec.setContent(R.id.menu);
        tabSpec.setIndicator("Today's Menu");
        tabHost.addTab(tabSpec);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("orders");
        tabSpec1.setContent(R.id.orders);
        tabSpec1.setIndicator("Your Orders");
        tabHost.addTab(tabSpec1);
        /**end of setting of tabs**/

        /*********Initialising components******/
        totalCost  = (TextView) findViewById(R.id.total);
        myOrders = (Button) findViewById(R.id.myOrders);
        placeOrders = (Button) findViewById(R.id.placeOrder);
        myOrders.setOnClickListener(this);
        placeOrders.setOnClickListener(this);
        foodAdapter = new FoodAdapter(this, R.layout.custom_list);
        orderAdapter = new OrderAdapter(this, R.layout.custom_order_list);
        /***************************************/

        /***Invoking server to give me menu items*/
        /** Background is a class which runs in new thread*/
        Background bg = new Background();
        bg.execute(localhost+"menu.php"); //here I'm passing the URL of menu.php
        /** Goto Background class*/
        /***************************************/

    }

    private void setOrderList(List<Orders> newOrders) {
           ordersList = newOrders;
        if (!ordersList.isEmpty()) {
            orderAdapter.addToList(ordersList);
            orderListView.setAdapter(orderAdapter);
            Toast.makeText(this, "Items added to Your orders!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Orders to add", Toast.LENGTH_SHORT).show();
        }
    }

    public void setList(List<Food> list) {
        foodList = list;
        if (!foodList.isEmpty()) {
            foodAdapter.addToList(foodList);
            runOnUiThread(new Runnable() {
                public void run() {
                    foodAdapter.notifyDataSetChanged();
                }
            });
            listView.setAdapter(foodAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Menu.this,foodList.get(position).getFood_name(),Toast.LENGTH_SHORT).show();
                    Food myFood = foodList.get(position);
                    Intent i = new Intent(Menu.this, Display_food.class);
                    i.putExtra("food_id", myFood.getFood_id());
                    i.putExtra("food_name", myFood.getFood_name());
                    i.putExtra("food_cost", myFood.getFood_cost());
                    i.putExtra("food_descr", myFood.getFood_descr());
                    Bitmap bmp = myFood.getImgBit();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    i.putExtra("img", bytes);
                    startActivity(i);
                }
            });

        } else {
            Toast.makeText(this, "List is not yet set", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
        int pos = listView.getPositionForView(buttonView);
        if (pos != listView.INVALID_POSITION) {
            final Food foody = foodList.get(pos);
            foody.setSeleted(isChecked);
            if (foody.isSeleted()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(foody.getFood_name());
                alert.setMessage("Set Quantity!!");
                final EditText etQty = new EditText(this);
                etQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                etQty.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(etQty);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String getTEXT = etQty.getText().toString();

                        if (!getTEXT.isEmpty()) {
                            int qty = Integer.parseInt(getTEXT);
                            if (qty != 0) {
                                foody.setQty(qty);
                            } else {
                                foody.setQty(1);
                            }
                        } else {
                            foody.setQty(1);
                        }
                    }

                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buttonView.setChecked(false);
                    }
                });

                AlertDialog dialog = alert.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } else {
                foody.setSeleted(false);
                buttonView.setChecked(false);
            }

        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.myOrders) {
            total=0.0;
            ordersList.clear();
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).isSeleted()) {
                    Orders orders = new Orders(foodList.get(i).getFood_id(), foodList.get(i).getQty(), foodList.get(i).getFood_cost(), foodList.get(i).getFood_name(), foodList.get(i).getImgBit());
                    total+=foodList.get(i).getQty()*foodList.get(i).getFood_cost();
                    newOrders.add(orders);
                }
            }
                totalCost.setText("Total Cost: Rs."+total);
            for (int i = 0; i < ordersList.size(); i++) {
                Log.v("Selected Items are:", ordersList.get(i).getFoodName() + " " + ordersList.get(i).foodCost + " " + ordersList.get(i).getQty());
            }
            if (newOrders.isEmpty()) {
                Toast.makeText(this, "No Items selected", Toast.LENGTH_SHORT).show();
                totalCost.setText("Total Cost: Rs."+0.0);
            } else {
                setOrderList(newOrders);
            }
        }
        if (v.getId() == R.id.placeOrder) {
            if (newOrders.isEmpty()) {
                Toast.makeText(this, "Please order something", Toast.LENGTH_SHORT).show();
            } else {
                PostOrders postOrders = new PostOrders(ordersList,Menu.this,CustomerId,total);
                postOrders.execute(localhost + "postOrders.php");
                finish();
            }
        }
    }

    /** BACKGROUND Class Starts*/
    class Background extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(Menu.this); //Initialising progress dialog box

        InputStream inputStream = null;
        String result = "";

        @Override
        protected void onPreExecute() {
            /**This starts the process,Displays progress dialog box*/
            dialog.setMessage("Loading..");
            dialog.show();
        }


        @Override
        protected Void doInBackground(String... urls) {
            /** Actual process is done here*/
            /** urls is the URL we passed in execute()*/
            HttpEntity entity; //This holds the entire content of the menu.php page
            /** Why for loop is urls is array of string*/
            for (String url : urls) {
                try {
                    /** Http Client is to establish the connection to server*/
                    HttpClient client = new DefaultHttpClient();
                    /** I'm using POST method however not posting anything.. */
                    HttpPost post = new HttpPost(url);
                    /** This returns the response you can get response code here but I'm not getting it..*/
                    HttpResponse response = client.execute(post);
                    /** entity retrieves RAW content of menu.php*/
                    entity = response.getEntity();
                    /** passing the entity to inputstream to read the content of menu.php*/
                    inputStream = entity.getContent();

                } catch (IOException e) {
                    Toast.makeText(Menu.this, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                try {

                    BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));
                    /**Making the RAW content to String*/
                    StringBuilder sBuilder = new StringBuilder();
                   /**Reading the content line by line*/
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        sBuilder.append(line + "\n");
                        inputStream.close();
                        /**Storing the readable content in result*/
                        result = sBuilder.toString();
                        /**This result holds the Readable format of the Menu.php*/
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
              /** That's all go Down*/
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            List<Food> menuList = new ArrayList<Food>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                Food food[] = new Food[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    Bitmap icon = null;
                    JSONObject jObject = jsonArray.getJSONObject(i);

                    int food_id = jObject.getInt("FoodId");
                    String food_name = jObject.getString("FoodName");
                    float food_cost = (float) jObject.getDouble("Cost");
                    String food_descr = jObject.getString("Descr");
                    String food_img = jObject.getString("ImgUrl");
                    try {
                        InputStream in = new URL(food_img).openStream();
                        icon = BitmapFactory.decodeStream(in);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    food[i] = new Food(food_id, food_name, food_descr, food_cost, food_img, icon);

                }

                for (int i = 0; i < food.length; i++) {
                    Food foods = food[i];
                    foods.display();
                    menuList.add(foods);
                }
                dialog.dismiss();
                Menu.this.setList(menuList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
