package com.example.projectutsmobile2;

public class ItemDataModel {
    private String item_name;
    private String item_price;
    private String item_stock;
    private int category_imageId;
    private String category_name;


    public ItemDataModel(String itemName, String itemPrice, String itemStock, int categoryImageId, String categoryName) {
        this.item_name = itemName;
        this.item_price = itemPrice;
        this.item_stock = itemStock;
        this.category_imageId = categoryImageId;
        this.category_name = categoryName;
    }

    public String getItem_name() {
        return item_name;
    }
    public String getItem_price() {
        return item_price;
    }
    public String getItem_stock() {
        return item_stock;
    }
    public int getCategory_imageId() {
        return category_imageId;
    }
    public String getCategory_name() {
        return category_name;
    }
}
