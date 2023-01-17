package com.example.coffeebelgatest;

public class User {
    private Integer userId;
    private String email;
    private String username;
    private String password;

    public User(Integer userid, String email, String username, String password) {
        this.userId = userid;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
