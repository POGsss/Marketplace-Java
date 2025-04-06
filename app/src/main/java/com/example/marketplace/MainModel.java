package com.example.marketplace;

public class MainModel {
    // Class Properties
    String productName;
    String productDescription;
    String productPrice;
    String productImg;
    String productCategory;
    int productCount;
    String productCondition;
    String productFrom;
    String productWarranty;

    // Constructor With Parameters


    public MainModel(String productName, String productDescription, String productPrice, String productImg, String productCategory, int productCount, String productCondition, String productFrom, String productWarranty) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productCategory = productCategory;
        this.productCount = productCount;
        this.productCondition = productCondition;
        this.productFrom = productFrom;
        this.productWarranty = productWarranty;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getProductFrom() {
        return productFrom;
    }

    public void setProductFrom(String productFrom) {
        this.productFrom = productFrom;
    }

    public String getProductWarranty() {
        return productWarranty;
    }

    public void setProductWarranty(String productWarranty) {
        this.productWarranty = productWarranty;
    }
}
