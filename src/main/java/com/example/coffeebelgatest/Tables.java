package com.example.coffeebelgatest;

public class Tables {

    private Integer tableNumber;
    private String type;
    private String status;
    private Integer user_id;

    public Tables(Integer tableNumber, String type, String status, Integer user_id) {
        this.tableNumber = tableNumber;
        this.type = type;
        this.status = status;
        this.user_id = user_id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
