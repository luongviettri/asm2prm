package com.example.asm2.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Animal implements Serializable {
    private final Bitmap photo; //! ảnh động vật

    private final Bitmap photoBg; //! ảnh background

    private final String path; //! đường dẫn đến ảnh

    private  String phoneNumber; //! số điện thoại của động vật này

    private final String name; //! tên động vật

    private final String content; //! nội dung mô tả động vật

    private boolean isFav; //! động vật yêu thích

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isFav() {
        return isFav;
    }


    public Animal(String path, Bitmap photo, Bitmap photoBg, String name, String content, boolean isFav) {
        this.path = path;
        this.photo = photo;
        this.photoBg = photoBg;
        this.name = name;
        this.isFav = isFav;
        this.content = content;
        this.phoneNumber = "";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public Bitmap getPhotoBg() {
        return photoBg;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
}
