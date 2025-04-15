package com.example.marketplace;

public class MainModel {
    // Class Properties
    String uid;
    String productImg;
    String productName;
    String productPrice;
    String productDescription;
    String productCategory;
    int productStock;
    String productCondition;
    String productWarranty;
    String productFrom;

    // Constructor With Parameters
    public MainModel(String uid, String productCategory, String productCondition, String productDescription, String productFrom, String productImg, String productName, String productPrice, int productStock, String productWarranty) {
        this.uid = uid;
        this.productCategory = productCategory;
        this.productCondition = productCondition;
        this.productDescription = productDescription;
        this.productFrom = productFrom;
        this.productImg = productImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productWarranty = productWarranty;
    }

    // Constructor Without Parameters
    public MainModel() {

    }

    // Getters & Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductFrom() {
        return productFrom;
    }

    public void setProductFrom(String productFrom) {
        this.productFrom = productFrom;
    }

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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductWarranty() {
        return productWarranty;
    }

    public void setProductWarranty(String productWarranty) {
        this.productWarranty = productWarranty;
    }
}
