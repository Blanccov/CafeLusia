package com.example.coffeebelgatest;

public class Menus {

    private String productId;
    private String productName;
    private String type;
    private Double price;
    private String status;

    public Menus(String productId, String productName, String type, Double price, String status) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
