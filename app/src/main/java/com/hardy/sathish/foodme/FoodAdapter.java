package com.hardy.sathish.foodme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter {
    private List<Food> list = new ArrayList<Food>();
    private Context context;

    public FoodAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }


    static class ListHolder {
        TextView FOOD_NAME;
        CheckBox CHECK;
        ImageView FOOD_IMG;
    }

    public void addToList(List<Food> list) {
        this.list = list;
    }

    public int getCount() {
        return this.list.size();

    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list = convertView;
        ListHolder listHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list = inflater.inflate(R.layout.custom_list, parent, false);
            listHolder = new ListHolder();
            listHolder.FOOD_NAME = (TextView) list.findViewById(R.id.food_name);
            listHolder.CHECK = (CheckBox) list.findViewById(R.id.checkBox);
            listHolder.FOOD_IMG = (ImageView) list.findViewById(R.id.imageView);
            list.setTag(listHolder);

        } else {
            listHolder = (ListHolder) list.getTag();
        }
        Food food = this.list.get(position);
        listHolder.FOOD_IMG.setImageBitmap(food.getImgBit());
        listHolder.FOOD_NAME.setText(food.getFood_name());
        listHolder.CHECK.setOnCheckedChangeListener(null);
        listHolder.CHECK.setChecked(food.isSeleted());
        listHolder.CHECK.setOnCheckedChangeListener((Menu) context);
        return list;
    }

}