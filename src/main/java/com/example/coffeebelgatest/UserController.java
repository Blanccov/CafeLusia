package com.example.coffeebelgatest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class UserController implements Initializable {


    @FXML
    private Button coupons_btn;

    @FXML
    private AnchorPane coupons_form;

    @FXML
    private Button menu_btn;

    @FXML
    private TableColumn<?, ?> menu_col_price;

    @FXML
    private TableColumn<?, ?> menu_col_productName;

    @FXML
    private TableColumn<?, ?> menu_col_status;

    @FXML
    private TableColumn<?, ?> menu_col_type;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private TextField menu_search;

    @FXML
    private TableView<Menus> menu_tableView;

    @FXML
    private Button minimize;

    @FXML
    private Button orders_add;

    @FXML
    private TextField orders_amount;

    @FXML
    private Label orders_balance;

    @FXML
    private Button orders_btn;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_price;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_productname;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_quantity;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_type;

    @FXML
    private TextField orders_phone;

    @FXML
    private TextField orders_address;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private Button orders_pay;

    @FXML
    private ComboBox<?> orders_productName;

    @FXML
    private Spinner<Integer> orders_quantity;

    @FXML
    private Button orders_receipt;

    @FXML
    private Button orders_remove;

    @FXML
    private TableView<OrdersUser> orders_tableView;

    @FXML
    private Label orders_total;

    @FXML
    private Button reservations_btn;

    @FXML
    private TextField reservations_size;

    @FXML
    private TableColumn<Reservations, String> reservations_col_status;

    @FXML
    private TableColumn<Reservations, String> reservations_col_tableNumber;

    @FXML
    private TableColumn<Reservations, String> reservations_col_type;


    @FXML
    private AnchorPane reservations_form;


    @FXML
    private ComboBox<?> reservations_tableNumber;


    @FXML
    private TableView<Reservations> reservations_tableView;

    @FXML
    private Button signout_btn;

    @FXML
    private Label username;

    @FXML
    private AnchorPane main_form;


    @FXML
    private TableColumn<OrdersUser, String> status_col_price;

    @FXML
    private TableColumn<OrdersUser, String> status_col_productName;

    @FXML
    private TableColumn<OrdersUser, String> status_col_status;

    @FXML
    private TableColumn<OrdersUser, String> status_col_type;

    @FXML
    private TableView<OrdersUser> status_tableView;

    @FXML
    private TableColumn<?, ?> status_col_payment;

    private Connection connection;
    private PreparedStatement prepared;
    private Statement statement;
    private ResultSet result;

    //MENU
    public ObservableList<Menus> menuShowList(){
        ObservableList<Menus> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM menu WHERE status = 'Available'";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            Menus menu;

            while(result.next()){
                menu = new Menus(result.getString("id"), result.getString("product_name"),
                        result.getString("type"), result.getDouble("price"), result.getString("status"));

                listData.add(menu);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Menus> menuList;
    public void menuShow(){
        menuList = menuShowList();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuList);

    }

    //TABLE RESERVATIONS

    public void reservationAdd(){
        methodUserID();

        String sql = "INSERT INTO reservations (user_id, id, type, status, date)" + "VALUES(?, ?, ?, ?, ?)";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(reservations_tableNumber.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {
                if(reservations_tableView.getItems().isEmpty()){
                

                String setData = "UPDATE tables SET status = 'Reserved' WHERE id = '" + reservations_tableNumber.getSelectionModel().getSelectedItem() + "'";
                String setUserId = "UPDATE tables SET user_id = '" + userId + "' WHERE id = '" + reservations_tableNumber.getSelectionModel().getSelectedItem() + "'";

                statement = connection.createStatement();
                statement.executeUpdate(setData);
                statement.executeUpdate(setUserId);

                String reservationType = "";
                String reservationStatus = "";

                String checkData = "SELECT * FROM tables WHERE id = '" + reservations_tableNumber.getSelectionModel().getSelectedItem() + "'";

                statement = connection.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    reservationType = result.getString("type");
                    reservationStatus  = result.getString("status");
                }


                prepared = connection.prepareStatement(sql);
                prepared.setString(1, String.valueOf(userId));
                prepared.setString(2, (String) reservations_tableNumber.getSelectionModel().getSelectedItem());
                prepared.setString(3, reservationType);
                prepared.setString(4, String.valueOf(reservationStatus));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepared.setString(5, String.valueOf(sqlDate));




                    prepared.executeUpdate();

                    reservationShow();
                    reservationTableNumber();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("You have reservation yet");
                    alert.showAndWait();
            }}

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //TABLE SIZE

    public void reservationsSize(){

        String sql = "SELECT type FROM tables WHERE id = '"+ reservations_tableNumber.getSelectionModel().getSelectedItem() + "'";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            String tableSize = "";


            while(result.next()){
                tableSize = result.getString("type");

            }

            reservations_size.setText(String.valueOf(tableSize));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //RESERVATIONS CLEAR
    public void reservationClear(){

        reservations_tableNumber.getSelectionModel().clearSelection();

        String setData = "UPDATE tables SET status = 'Not reserved'";

        String setUserId = "UPDATE tables SET user_id = '1'";

        String sql = "DELETE FROM reservations WHERE user_id = '" + userId + "'";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(reservations_tableView.getItems().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("You don't have any reservation");
                alert.showAndWait();

            }else{

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to cancel reservation?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Reservation is canceled");
                    alert.showAndWait();

                    statement = connection.createStatement();
                    statement.executeUpdate(setData);
                    statement.executeUpdate(sql);
                    statement.executeUpdate(setUserId);

                    //SHOW DATA AFTER ADD
                    reservationShow();

                }else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Action is cancelled");
                    alert.showAndWait();
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //SHOW TABLE RESERVATIONS


    public ObservableList<Reservations> reservationShowList(){
        ObservableList<Reservations> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM reservations WHERE user_id='"+ userId +"'";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            Reservations reservations;

            while(result.next()){
                reservations = new Reservations(result.getInt("id"), result.getInt("user_id"), result.getInt("id"), result.getString("type"),
                        result.getString("status"), result.getDate("date"));

                listData.add(reservations);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Reservations> reservationList;
    public void reservationShow(){
        reservationList = reservationShowList();

        reservations_col_tableNumber.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        reservations_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        reservations_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        reservations_tableView.setItems(reservationList);

    }

    // SETING COMBOBOX FOR TABLE NUMBER

    public void reservationTableNumber(){
        String sql = "SELECT id FROM tables WHERE status = 'Not reserved'";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while(result.next()){
                listData.add(result.getString("id"));
            }

            reservations_tableNumber.setItems(listData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // ORDERS PRODUCTID SELECTOR FROM DATABASE MENU AND SETING CHECKBOX FPR PRODUCT NAME

    public void ordersProductID(){
        String sql = "SELECT product_name FROM menu WHERE status = 'Available'";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while(result.next()){
                listData.add(result.getString("product_name"));
            }

            orders_productName.setItems(listData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // CHANGING ODER QUANTITY
    private SpinnerValueFactory<Integer> spinner;

    public void ordersSpinner(){

        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0);

        orders_quantity.setValueFactory(spinner);
    }

    private int quantity;
    public void ordersQuantity(){
        quantity =  orders_quantity.getValue();
    }


    // ADD ORDER

    public void ordersAdd() {

        methodUserID();
        ordersCustomerID();
        orderTotal();
        showProductId();

        String sql = "INSERT INTO orders" + "(user_id, customer_id, product_id, product_name, type, price, quantity, phone, address, date, relase, payment)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(orders_productName.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {

                String orderType = "";
                double orderPrice = 0;

                String checkData = "SELECT * FROM menu WHERE product_name = '" + orders_productName.getSelectionModel().getSelectedItem() + "'";

                statement = connection.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    orderType = result.getString("type");
                    orderPrice = result.getDouble("price");
                }

                prepared = connection.prepareStatement(sql);
                prepared.setString(1, String.valueOf(userId));
                prepared.setString(2, String.valueOf(customerId));
                prepared.setString(3, String.valueOf(productId));
                prepared.setString(4, (String) orders_productName.getSelectionModel().getSelectedItem());
                prepared.setString(5, orderType);

                double totalPrice = orderPrice * quantity;

                prepared.setString(6, String.valueOf(totalPrice));
                prepared.setString(7, String.valueOf(quantity));
                prepared.setString(8, orders_phone.getText());
                prepared.setString(9, orders_address.getText());

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepared.setString(10, String.valueOf(sqlDate));
                prepared.setString(11, "In process");
                prepared.setString(12, "Waiting for payment");

                if(orders_phone.getText().length() != 9){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Phone has 9 characters ");
                    alert.showAndWait();
                }else if(orders_address.getText().isEmpty()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("We need your address to delivery! :D");
                    alert.showAndWait();
                }else{
                    prepared.executeUpdate();

                    orderDisplayTotal();
                    statusDisplayData();
                    orderDisplayData();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //PRODUCT ID

    String productId;

    public void showProductId(){
        String sql = "SELECT id FROM menu";

        connection = Database.connectDb();

        try {

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            if(result.next()){
                productId = result.getString("id");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // TOTAL PRICE FOR ORDER
    private double totalP = 0;

    public void orderTotal(){

        String sql = "SELECT SUM(price) FROM orders WHERE user_id = '" + userId + "' and customer_id ='" + customerId + "'";

        connection = Database.connectDb();

        try {

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            if(result.next()){
                totalP = result.getDouble("SUM(price)");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //PAYMANT FOR ORDER BUTTON

    public void orderPay(){

        String sql = "INSERT INTO product (id, total, date) VALUES(?,?,?)";

        connection = Database.connectDb();

        try{

            Alert alert;

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure about this?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    String setData = "UPDATE orders SET payment = 'done' WHERE customer_id = '" + customerId + "'";

                    statement = connection.createStatement();
                    statement.executeUpdate(setData);

                    prepared = connection.prepareStatement(sql);
                    prepared.setString(1, String.valueOf(customerId));
                    prepared.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepared.setString(3, String.valueOf(sqlDate));

                    prepared.executeUpdate();

                    orders_total.setText("0.0zl");

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Already done ;D");
                    alert.showAndWait();

                    statusDisplayData();
                    orderDisplayData();

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();

                    statusDisplayData();
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // DISPLAYING TOTAL PIRCE IN APP

    public void orderDisplayTotal(){
        orderTotal();
        orders_total.setText(String.valueOf(totalP) + "zł");
    }

    //REMOVE ORDER BUTTON

    public void orderRemove(){

        String sql = "DELETE FROM orders WHERE id =" + item;

        connection = Database.connectDb();

        try {
            Alert alert;

            if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Select item");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure about this?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Removed");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //ORDER SELECTOR DATA

    private int item;
    public void orderSelectData(){

        OrdersUser order = orders_tableView.getSelectionModel().getSelectedItem();
        int i = orders_tableView.getSelectionModel().getSelectedIndex();

        if((i - 1) < -1)
            return;

        item = order.getId();
    }

    //DISPLAYING ORDERS DATA IN APP

    private ObservableList<OrdersUser> ordersData;
    public void orderDisplayData(){
        ordersData = ordersUserList();

        orders_col_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orders_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        orders_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        orders_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        orders_tableView.setItems(ordersData);
    }



    // CUSOTMER ID FOR ORDERS

    private int customerId;

    public void ordersCustomerID(){

        String sql = "SELECT customer_id FROM orders";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            while(result.next()){
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT id FROM product";

            statement = connection.createStatement();
            result = statement.executeQuery(checkData);

            int customerInfoID = 0;

            while(result.next()){
                customerInfoID = result.getInt("id");
            }

            if(customerId == 0){
                customerId += 1;
            }else if(customerId == customerInfoID){
                customerId += 1;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // USER ID FOR ORDERSUSER

    private int userId;

    public void methodUserID(){

        String sql = "SELECT id FROM user WHERE username = '"+ username.getText() + "'";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();


            while(result.next()){
                userId = result.getInt("id");
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // ADDING ORDER LIST TO DATABASE

    public ObservableList<OrdersUser> ordersUserList(){

        ordersCustomerID();

        ObservableList<OrdersUser> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders WHERE user_id = '" + userId + "' and customer_id = '"+ customerId +"'";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            OrdersUser orderuser;

            while(result.next()){
                orderuser = new OrdersUser(result.getInt("id"),
                        result.getInt("user_id"),
                        result.getInt("customer_id"),
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getInt("quantity"),
                        result.getInt("phone"),
                        result.getString("address"),
                        result.getDate("date"),
                        result.getString("relase"),
                        result.getString("payment"));

                listData.add(orderuser);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return listData;
    }



    //DISPLAYING ORDERS STATUS DATA IN APP

    private ObservableList<OrdersUser> statusData;
    public void statusDisplayData(){
        statusData = statusUserList();

        status_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        status_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        status_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status_col_status.setCellValueFactory(new PropertyValueFactory<>("relase"));
        status_col_payment.setCellValueFactory(new PropertyValueFactory<>("payment"));

        status_tableView.setItems(statusData);
    }

    //DISPLAY FOR STATUS

    public ObservableList<OrdersUser> statusUserList(){

        ObservableList<OrdersUser> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders WHERE user_id = '" + userId + "' and CURRENT_DATE";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            OrdersUser orderuser;

            while(result.next()){
                orderuser = new OrdersUser(result.getInt("id"),
                        result.getInt("user_id"),
                        result.getInt("customer_id"),
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getInt("quantity"),
                        result.getInt("phone"),
                        result.getString("address"),
                        result.getDate("date"),
                        result.getString("relase"),
                        result.getString("payment"));

                listData.add(orderuser);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return listData;
    }


    //SEARCH MENU

    public void menuSearch(){
        FilteredList<Menus> filter = new FilteredList<>(menuList, e -> true);

        menu_search.textProperty().addListener((obervable, newvalue, oldvalue) -> {
            filter.setPredicate(precidateMenus -> {

                if(newvalue.isEmpty() || newvalue == null){
                    return true;
                }

                String searchKey = newvalue.toLowerCase();

                if(precidateMenus.getProductId().toLowerCase().contains(searchKey)){
                    return true;
                }else if(precidateMenus.getProductName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(precidateMenus.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(precidateMenus.getPrice().toString().contains(searchKey)){
                    return true;
                }else if(precidateMenus.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else{

                    return false;}
            });
        });

        SortedList<Menus> sortedList = new SortedList<>(filter);

        sortedList.comparatorProperty().bind(menu_tableView.comparatorProperty());

        menu_tableView.setItems(sortedList);

    }




    // SWITCHING VISIBILITY WITH BUTTONS FOR FORMS

    public void switchForm(ActionEvent event){
       if(event.getSource() == menu_btn){
            menu_form.setVisible(true);
            reservations_form.setVisible(false);
            orders_form.setVisible(false);
            coupons_form.setVisible(false);

            menuShow();
            menuSearch();

            menu_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle(".nav-btn");
            coupons_btn.setStyle(".nav-btn");
        }else if(event.getSource() == reservations_btn){
            menu_form.setVisible(false);
            reservations_form.setVisible(true);
            orders_form.setVisible(false);
            coupons_form.setVisible(false);

           reservationTableNumber();
           methodUserID();
           reservationShow();

            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            orders_btn.setStyle(".nav-btn");
            coupons_btn.setStyle(".nav-btn");
        } else if(event.getSource() == orders_btn){
            menu_form.setVisible(false);
            reservations_form.setVisible(false);
            orders_form.setVisible(true);
            coupons_form.setVisible(false);

           ordersProductID();
           ordersSpinner();
           statusDisplayData();
           orderDisplayData();
           orderDisplayTotal();

            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            coupons_btn.setStyle(".nav-btn");
        }else if(event.getSource() == coupons_btn){
            menu_form.setVisible(false);
            reservations_form.setVisible(false);
            orders_form.setVisible(false);
            coupons_form.setVisible(true);

           statusDisplayData();

            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle(".nav-btn");
            coupons_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
    }}



    // LOGOUT FUNCTION
    private double x = 0;
    private double y = 0;

    public void logout(){

        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure about this?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                signout_btn.getScene().getWindow().hide();

                // HELLO-VIEW.FXML SCENE LIKE IN HELLOCONTROLLER
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8f);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void displayUsername(){
//        USERNAME CHANGE

        String user = Data.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

        menuShow();
        menuSearch();

        reservationTableNumber();
        reservationShow();
        reservationShow();

        orderDisplayData();
        ordersProductID();
        ordersSpinner();
        orderDisplayTotal();

        statusDisplayData();

        methodUserID();

    }
}
