package com.example.marketplace;

public class AddedModel {
    // Class Properties
    String productImg;
    String productName;
    String uid;

    // Constructor With Parameters
    public AddedModel(String productImg, String productName, String uid) {
        this.productImg = productImg;
        this.productName = productName;
        this.uid = uid;
    }

    // Constructor Without Parameters
    public AddedModel() {

    }

    // Getters & Setters
    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
