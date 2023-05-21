package com.example.fabset;

public class Product {
    private String title;
    private String price;
    private int imageRes;

    public Product(String title, String price, int imageRes) {
        this.title = title;
        this.price = price;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImageRes() {
        return imageRes;
    }
}

