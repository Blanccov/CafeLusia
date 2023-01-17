module com.example.coffeebelgatest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.naming;
    requires java.sql;
    requires java.xml;


    opens com.example.coffeebelgatest to javafx.fxml;
    exports com.example.coffeebelgatest;
}