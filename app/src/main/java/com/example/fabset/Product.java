package com.example.fabset;

import android.graphics.Bitmap;

public class Product {
    private int id;
    private String name;
    private String price;
    private String category;
    private String subcategory;
    private String description;
    private String tag;
    private Bitmap image;

    public Product(int id, String title, String price, String cat, String subcat, String desc, String tg, Bitmap img) {
        this.id = id;
        this.name = title;
        this.price = price;
        this.category = cat;
        this.subcategory = subcat;
        this.description = desc;
        this.tag = tg;
        this.image = img;
    }
    public Product(int id, String title, Bitmap img){
        this.id = id;
        this.name = title;
        this.image = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

