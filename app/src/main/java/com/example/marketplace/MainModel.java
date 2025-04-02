package com.example.marketplace;

public class MainModel {
    // Class Properties
    String productName;
    String productDesc;
    String productPrice;
    String productImg;
    int productCount;

    // Constructor With Parameters
    public MainModel(String productName, String productDesc, String productPrice, String productImg, int productCount) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productCount = productCount;
    }

    // Constructor Without Parameters
    public MainModel() {

    }

    // Getters & Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
