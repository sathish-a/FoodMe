package com.hardy.sathish.foodme;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by sathish on 24/07/15.
 */
public class Food {

    int food_id,qty;
    String food_name,food_descr,food_img;
    float food_cost;
    boolean seleted = false;
    Bitmap imgBit;

    public Food(int food_id,String food_name,String food_descr,float food_cost,String food_img,Bitmap imgBit){

        this.food_id=food_id;
        this.food_name=food_name;
        this.food_descr=food_descr;
        this.food_cost=food_cost;
        this.food_img=food_img;
        this.imgBit=imgBit;

    }

    void display(){
        Log.v("Food:","Food_id:"+food_id+",Food_name:"+food_name+",Food_Desc:"+food_descr+",Food_cost:"+food_cost+",Link:"+food_img);
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public boolean isSeleted() {
        return seleted;
    }

    public void setSeleted(boolean seleted) {
        this.seleted = seleted;
    }

    public float getFood_cost() {
        return food_cost;
    }

    public void setFood_cost(float food_cost) {
        this.food_cost = food_cost;
    }

    public String getFood_descr() {
        return food_descr;
    }

    public void setFood_descr(String food_descr) {
        this.food_descr = food_descr;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getFood_img() {
        return food_img;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

    public Bitmap getImgBit() {
        return imgBit;
    }

    public void setImgBit(Bitmap imgBit) {
        this.imgBit = imgBit;
    }
}
