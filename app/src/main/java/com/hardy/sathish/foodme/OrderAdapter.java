package com.hardy.sathish.foodme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sathish on 27/07/2015.
 */
public class OrderAdapter extends ArrayAdapter {
    private List<Orders> ordersList = new ArrayList<Orders>();
    private Context context;


    public OrderAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public void addToList(List<Orders> list) {

        ordersList = list;
    }

    public int getCount() {
        return this.ordersList.size();
    }

    static class OrderHolder {
        TextView Food_Name;
        TextView Food_Cost;
        ImageView Food_Image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        OrderHolder orderHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_order_list, parent, false);
            orderHolder = new OrderHolder();
            orderHolder.Food_Name = (TextView) view.findViewById(R.id.food_name);
            orderHolder.Food_Cost = (TextView) view.findViewById(R.id.cost);
            orderHolder.Food_Image = (ImageView)view.findViewById(R.id.orderImg);
            view.setTag(orderHolder);
        } else {
            orderHolder = (OrderHolder) view.getTag();
        }

        Orders order = ordersList.get(position);
        orderHolder.Food_Image.setImageBitmap(order.getFoodImg());
        orderHolder.Food_Name.setText(order.getFoodName());
        orderHolder.Food_Cost.setText(order.getQty()+"X" + order.getFoodCost()+"=" + order.getQty()*order.getFoodCost());


        return view;


    }
}
