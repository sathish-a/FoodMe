package com.hardy.sathish.foodme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Bill extends Activity implements View.OnClickListener {

    TextView amt;
    Button again,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        amt = (TextView) findViewById(R.id.amt);
        again = (Button) findViewById(R.id.again);
        exit = (Button) findViewById(R.id.exit);
        double amnt = getIntent().getExtras().getDouble("amount", -1);
        amt.setText("Rs."+amnt);
        again.setOnClickListener(this);
        exit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.again:
                startActivity(new Intent(this,Login.class));
                finish();
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
    }
}
