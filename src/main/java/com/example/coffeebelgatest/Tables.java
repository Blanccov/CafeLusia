package com.example.coffeebelgatest;

import java.util.Date;

public class Tables {

    private Integer tableNumber;
    private String type;
    private String status;
    private Integer phone;
    private Date date;

    public Tables(Integer tableNumber, String type, String status,Integer phone, Date date) {
        this.tableNumber = tableNumber;
        this.type = type;
        this.status = status;
        this.phone = phone;
        this.date = date;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
