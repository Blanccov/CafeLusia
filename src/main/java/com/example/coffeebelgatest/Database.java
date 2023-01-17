package com.example.coffeebelgatest;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/coffeebelga", "root", "");
            return connection;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
