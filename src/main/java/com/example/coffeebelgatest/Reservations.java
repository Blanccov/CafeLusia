package com.example.coffeebelgatest;

import java.util.Date;

public class Reservations {

    private Integer Id;
    private Integer userId;
    private Integer tableNumber;
    private String type;
    private String status;
    private Date date;

    public Reservations(Integer Id, Integer userId, Integer tableNumber, String type, String status, Date date) {
        this.Id = userId;
        this.userId = userId;
        this.tableNumber = tableNumber;
        this.type = type;
        this.status = status;
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
