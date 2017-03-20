package com.hardy.sathish.foodme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Display_food extends Activity implements View.OnClickListener {
    TextView name, descr, cost;

    Button ok;
    int food_id;
    String food_name,food_descr;
    float food_cost;
    ImageView food_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);
        name = (TextView) findViewById(R.id.tvName);
        descr = (TextView) findViewById(R.id.tvDescr);
        cost = (TextView) findViewById(R.id.tvCost);
        food_img = (ImageView)findViewById(R.id.food_img);
        ok = (Button) findViewById(R.id.ok);


        byte[] byteArray = getIntent().getByteArrayExtra("img");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


        food_img.setImageBitmap(bmp);
        food_id = getIntent().getExtras().getInt("food_id");
        food_name = getIntent().getExtras().getString("food_name");
        food_descr = getIntent().getExtras().getString("food_descr");
        food_cost = getIntent().getExtras().getFloat("food_cost");
        name.setText(""+food_name);
        descr.setText(""+food_descr);
        cost.setText("Cost: Rs."+food_cost);
        ok.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.ok)
        {
            finish(); 
        }


    }
}
