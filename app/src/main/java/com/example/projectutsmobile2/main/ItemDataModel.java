package com.example.projectutsmobile2;

import android.util.Log;

public class ItemDataModel {
    private int item_id;
    private String item_name;
    private String item_price;
    private String item_stock;
    private int category_id;
    private int category_imageId;
    private String category_name;


    public ItemDataModel(int itemId, String itemName, String itemPrice, String itemStock, int categoryId, int categoryImageId, String categoryName) {
        this.item_id = itemId;
        this.item_name = itemName;
        this.item_price = itemPrice;
        this.item_stock = itemStock;
        this.category_id = categoryId;
        this.category_imageId = categoryImageId;
        this.category_name = categoryName;
    }

    public int getItem_id() { return item_id; }
    public String getItem_name() {
        return item_name;
    }
    public String getItem_price() {
        return item_price;
    }
    public String getItem_stock() {
        return item_stock;
    }
    public int getCategory_id() {return category_id;}
    public int getCategory_imageId() {
        return category_imageId;
    }
    public String getCategory_name() {
        return category_name;
    }
}
