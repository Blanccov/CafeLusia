package com.example.coffeebelgatest;

import java.util.Date;

public class OrdersUser {

    private Integer id;
    private Integer user_id;
    private Integer customerId;
    private Integer productId;
    private String productName;
    private String type;
    private Double price;
    private Integer quantity;
    private Integer phone;
    private String address;
    private Date date;
    private String relase;
    private String payment;

    public OrdersUser(Integer id,Integer user_id, Integer customerId, Integer productId, String productName, String type, Double price, Integer quantity, Integer phone, String address, Date date, String relase, String payment) {
        this.id = id;
        this.user_id = user_id;
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.relase = relase;
        this.payment = payment;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getProductId() {
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

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public String getRelase() {
        return relase;
    }

    public String getPayment() {
        return payment;
    }
}
