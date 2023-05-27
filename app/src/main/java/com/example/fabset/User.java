package com.example.fabset;

import android.graphics.Bitmap;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String address;
    private String phone;
    private Bitmap image;

    public User(int id, String name, String password,String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public User(int id, String name, String password,String email, String address, String phone, Bitmap img) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = img;
    }
    public User(int id, String name, String password,String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public User(int id, String name, String password,Bitmap img) {
        this.id = id;
        this.name = name;
        this.image = img;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
