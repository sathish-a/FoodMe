package com.hardy.sathish.foodme;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Sathish on 25/07/2015.
 */
public class Orders {
    int foodId;
    float foodCost;
    int qty;
    String foodName;
    Bitmap foodImg;

    public Orders(int foodId, int qty, float foodCost, String foodName,Bitmap foodImg) {
        this.foodId = foodId;
        this.qty = qty;
        this.foodCost = foodCost;
        this.foodName = foodName;
        this.foodImg = foodImg;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public float getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(float foodCost) {
        this.foodCost = foodCost;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    void display() {
        Log.v("Food:", "Food_id:" + foodId + ",Food_name:" + foodName + ",Qty:" + qty + ",Food_cost:" + foodCost);
    }

    public Bitmap getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(Bitmap foodImg) {
        this.foodImg = foodImg;
    }
}
