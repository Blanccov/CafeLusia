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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DashboardController implements Initializable {
    @FXML
    private Button customers_btn;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_address;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_userID;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_date;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_phone;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_price;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_productID;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_productName;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_quantity;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_type;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_customerId;

    @FXML
    private TableColumn<OrdersUser, String> customers_col_relase;

    @FXML
    private Button customers_update;

    @FXML
    private ComboBox<?> customers_relase;

    @FXML
    private TextField customers_relaseField;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TextField customers_serach;

    @FXML
    private TableView<OrdersUser> customers_tableView;

    @FXML
    private Label dashboard_AT;

    @FXML
    private Label dashboard_CT;

    @FXML
    private Label dashboard_ET;

    @FXML
    private AreaChart<?, ?> dashboard_ICchart;

    @FXML
    private BarChart<?, ?> dashboard_NOOCchart;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashborad_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_add;

    @FXML
    private Button menu_btn;

    @FXML
    private Button menu_clear;

    @FXML
    private TableColumn<Menus, String> menu_col_price;

    @FXML
    private TableColumn<Menus, String> menu_col_productID;

    @FXML
    private TableColumn<Menus, String> menu_col_productName;

    @FXML
    private TableColumn<Menus, String> menu_col_status;

    @FXML
    private TableColumn<Menus, String> menu_col_type;

    @FXML
    private Button menu_delete;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private TextField menu_price;


    @FXML
    private TextField menu_productName;

    @FXML
    private TextField menu_search;

    @FXML
    private ComboBox<String> menu_status;

    @FXML
    private TableView<Menus> menu_tableView;

    @FXML
    private ComboBox<String> menu_type;

    @FXML
    private Button menu_update;

    @FXML
    private Button minimize;

    @FXML
    private Label nav_username;

    @FXML
    private Button order_add;

    @FXML
    private Button order_delete;

    @FXML
    private Button order_pay;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<Integer> order_quantity;

    @FXML
    private Button order_receipt;

    @FXML
    private Button order_remove;

    @FXML
    private Label order_total;

    @FXML
    private TextField orders_amount;

    @FXML
    private Button orders_btn;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_price;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_productID;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_productName;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_quantity;

    @FXML
    private TableColumn<OrdersUser, String> orders_col_type;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private TextField orders_search;

    @FXML
    private TableView<OrdersUser> orders_tableView;

    @FXML
    private Button reservation_remove;

    @FXML
    private Button reservation_add;

    @FXML
    private Button reservation_clear;

    @FXML
    private Button reservation_delete;

    @FXML
    private ComboBox<String> reservation_status;

    @FXML
    private TextField reservation_tableNumber;

    @FXML
    private ComboBox<String> reservation_type;

    @FXML
    private Button reservation_update;

    @FXML
    private Button reservations_btn;

    @FXML
    private TableColumn<Tables, String> reservations_col_status;

    @FXML
    private TableColumn<Tables, String> reservations_col_tableNumber;

    @FXML
    private TableColumn<Tables, String> reservations_col_type;

    @FXML
    private TableColumn<Tables, String> reservations_col_userID;

    @FXML
    private TableColumn<Tables, String> reservations_col_date;

    @FXML
    private AnchorPane reservations_form;

    @FXML
    private TextField reservations_serach;

    @FXML
    private Label order_balance;

    @FXML
    private TableView<Tables> reservations_tableView;

    @FXML
    private Button signout_btn;

    private Connection connection;
    private PreparedStatement prepared;
    private Statement statement;
    private ResultSet result;

    //DASHBOARD

    //CUSTOMERS TODAY
    public void dashboardCT(){

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT COUNT(id) FROM product WHERE date = '" + sqlDate + "'" ;


        int ct = 0;

        connection = Database.connectDb();

        try{

            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            if(result.next()){
                ct = result.getInt("COUNT(id)");
            }

            dashboard_CT.setText(String.valueOf(ct));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardET(){

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product WHERE date = '" + sqlDate + "'";

        connection = Database.connectDb();

        double et = 0;

        try{
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            if(result.next()){
                et = result.getDouble("SUM(total)");
            }

            dashboard_ET.setText(String.valueOf(et) + "zł");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardAT(){

        String sql = "SELECT COUNT(id) FROM tables WHERE status = 'Not reserved'";

        connection = Database.connectDb();

        int at = 0;

        try{
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            if(result.next()){
                at = result.getInt("COUNT(id)");
            }

            dashboard_AT.setText(String.valueOf(at));

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //CHARTS FOR DASHBOARD

    public void dashboardNOOC(){
        dashboard_NOOCchart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM product GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

        connection = Database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            while (result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_NOOCchart.getData().add(chart);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardIC(){
        dashboard_ICchart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM product GROUP BY date ORDER BY TIMESTAMP(total) ASC LIMIT 5";

        connection = Database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            while (result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));
            }

            dashboard_ICchart.getData().add(chart);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // ADDING ITEMS TO DATABASE MENU

    public void menuAdd(){

        String sql = "INSERT INTO menu (product_name, type, price, status)" + "VALUES(?, ?, ?, ?)";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            prepared.setString(1, menu_productName.getText());
            prepared.setString(2, (String) menu_type.getSelectionModel().getSelectedItem());
            prepared.setString(3, menu_price.getText());
            prepared.setString(4, (String) menu_status.getSelectionModel().getSelectedItem());

            Alert alert;

            if(menu_productName.getText().isEmpty()
                    || menu_type.getSelectionModel().getSelectedItem() == null
                    || menu_price.getText().isEmpty()
                    || menu_status.getSelectionModel().getSelectedItem() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            } else{

                String checkData = "SELECT id FROM menu WHERE product_name = '" + menu_productName.getText() + "'";
                connection = Database.connectDb();

                statement = connection.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(menu_productName.getText() + " is existing");
                    alert.showAndWait();
                }else{
                    prepared.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText(menu_productName.getText() + " is added");
                    alert.showAndWait();

                    //SHOW DATA AFTER ADD
                    menuShow();
                    //CLEAR FIELDS AFTER ADD
                    menuClear();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    //UPDATE DATABASE LIST

    public void menuUpdate(){
        String sql = "UPDATE menu SET product_name = '"+ menu_productName.getText() +
                "', type ='" + menu_type.getSelectionModel().getSelectedItem() +
                "', price = '" + menu_price.getText() +
                "', status = '" + menu_status.getSelectionModel().getSelectedItem() +
                "' WHERE product_name = '" + menu_productName.getText() + "'";

        connection = Database.connectDb();

        try{

            statement = connection.createStatement();

            Alert alert;

            if(menu_productName.getText().isEmpty()
                    || menu_type.getSelectionModel().getSelectedItem() == null
                    || menu_price.getText().isEmpty()
                    || menu_status.getSelectionModel().getSelectedItem() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to update product " + menu_productName.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Update is done");
                    alert.showAndWait();

                    statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    //SHOW DATA AFTER ADD
                    menuShow();
                    //CLEAR FIELDS AFTER ADD
                    menuClear();
                }else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Update is cancelled");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // CLEAR FIELDS
    public void menuClear(){

        menu_productName.setText("");
        menu_type.getSelectionModel().clearSelection();
        menu_price.setText("");
        menu_status.getSelectionModel().clearSelection();
    }

    // DELETE ITEM FORM DATABASE

    public void menuDelete(){
        String sql = "DELETE FROM menu WHERE product_name = '" + menu_productName.getText() + "'";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(menu_productName.getText().isEmpty()
                    || menu_type.getSelectionModel() == null
                    || menu_price.getText().isEmpty()
                    || menu_status.getSelectionModel() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to delete product " + menu_productName.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Delete is done");
                    alert.showAndWait();

                    statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    //SHOW DATA AFTER ADD
                    menuShow();
                    //CLEAR FIELDS AFTER ADD
                    menuClear();
                }else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Delete is cancelled");
                    alert.showAndWait();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //SHOWING MENU LIST DATABASE

    public ObservableList<Menus> menuShowList(){
        ObservableList<Menus> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM menu";

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

        menu_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        menu_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        menu_tableView.setItems(menuList);

    }

    //SELECT FROM TABLE

    public void menuSelect(){

        Menus menu = menu_tableView.getSelectionModel().getSelectedItem();

        int i = menu_tableView.getSelectionModel().getSelectedIndex();

        if((i - 1) < - 1){
            return;
        }

        menu_productName.setText(menu.getProductName());
        menu_price.setText(String.valueOf(menu.getPrice()));
        menu_type.setValue(String.valueOf(menu.getType()));
        menu_status.setValue(String.valueOf(menu.getStatus()));
    }


    // MENU TYPE AND STATUS SELECTOR

    private String[] types = {"Meals", "Drinks"};

    public void menuTypes(){
        List<String> listTypes = new ArrayList<>();

        for(String data: types){
            listTypes.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listTypes);
        menu_type.setItems((listData));
    }
    private String[] status = {"Available", "Not available"};

    public void menuStatus(){
        List<String> listStatus = new ArrayList<>();

        for(String data: status){
            listStatus.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatus);
        menu_status.setItems((listData));

    }




    //TABLE RESERVATIONS

    public void reservationAdd(){

        String sql = "INSERT INTO tables (id, type, status, user_id)" + "VALUES(?, ?, ?, ?)";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            prepared.setString(1, reservation_tableNumber.getText());
            prepared.setString(2, (String) reservation_type.getSelectionModel().getSelectedItem());
            prepared.setString(3, (String) reservation_status.getSelectionModel().getSelectedItem());
            prepared.setString(4, String.valueOf(1));

            Alert alert;
            Pattern pattern = Pattern.compile("[a-zA-Z]");
            Matcher matcher = pattern.matcher(reservation_tableNumber.getText());

            if(reservation_tableNumber.getText().isEmpty()
                    || reservation_type.getSelectionModel().getSelectedItem() == null
                    || reservation_status.getSelectionModel().getSelectedItem() == null || matcher.find() && matcher.group().matches(reservation_tableNumber.getText())){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks correctly");
                alert.showAndWait();
            } else{

                String checkData = "SELECT id FROM tables WHERE id = '" + reservation_tableNumber.getText() + "'";
                connection = Database.connectDb();

                statement = connection.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Table: " + reservation_tableNumber.getText() + " is existing");
                    alert.showAndWait();
                }else{
                    prepared.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText(reservation_tableNumber.getText() + " is added");
                    alert.showAndWait();

                    //SHOW DATA AFTER ADD
                    reservationShow();

                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //UPDATE DATABASE LIST

    public void reservationUpdate(){
        String sql = "UPDATE tables SET type = '"+ reservation_type.getSelectionModel().getSelectedItem() +
                "', status = '" + reservation_status.getSelectionModel().getSelectedItem() +
                "' WHERE id = '" + reservation_tableNumber.getText() + "'";

        connection = Database.connectDb();

        try{

            statement = connection.createStatement();

            Alert alert;

            if(reservation_tableNumber.getText().isEmpty()
                    || reservation_type.getSelectionModel().getSelectedItem() == null
                    || reservation_status.getSelectionModel().getSelectedItem() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to update reservation " + menu_productName.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Update is done");
                    alert.showAndWait();

                    statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    //SHOW DATA AFTER ADD
                    reservationShow();

                }else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Update is cancelled");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // CLEAR FIELDS
    public void reservationClear(){

        String setData = "UPDATE tables SET status = 'Not reserved'";

        String setUserId = "UPDATE tables SET user_id = '1'";

        String removeData = "DELETE FROM reservations WHERE status = 'Reserved'";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(reservations_tableView.getColumns().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Are u sure about change all to not reserved??");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Action is done");
                    alert.showAndWait();

                    statement = connection.createStatement();
                    statement.executeUpdate(setData);
                    statement.executeUpdate(setUserId);
                    statement.executeUpdate(removeData);

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


    public ObservableList<Tables> reservationShowList(){
        ObservableList<Tables> listData = FXCollections.observableArrayList();

        String sql = "SELECT tables.id, tables.type, tables.status, reservations.date, reservations.phone FROM tables INNER JOIN reservations ON tables.user_id = reservations.user_id";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            Tables table;

            while(result.next()){
                table = new Tables(result.getInt("id"), result.getString("type"),
                        result.getString("status"), result.getInt("phone"), result.getDate("date"));

                listData.add(table);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Tables> reservationList;
    public void reservationShow(){
        reservationList = reservationShowList();

        reservations_col_tableNumber.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        reservations_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        reservations_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        reservations_col_userID.setCellValueFactory(new PropertyValueFactory<>("phone"));
        reservations_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        reservations_tableView.setItems(reservationList);

    }


    //SELECT FROM TABLE

    public void reservationSelect(){

        Tables table = reservations_tableView.getSelectionModel().getSelectedItem();

        int i = reservations_tableView.getSelectionModel().getSelectedIndex();

        if((i - 1) < - 1){
            return;
        }

        reservation_tableNumber.setText(String.valueOf(table.getTableNumber()));
        reservation_type.setValue(String.valueOf(table.getType()));
        reservation_status.setValue(String.valueOf(table.getStatus()));
    }


    // RESERVATIONS TYPE AND STATUS SELECTOR

    private String[] typesRe = {"2 - person", "4 - person", "6 - person", "8 - person"};

    public void reservationsTypes(){
        List<String> listTypesRe = new ArrayList<>();

        for(String data: typesRe){
            listTypesRe.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listTypesRe);
        reservation_type.setItems((listData));
    }
    private String[] statusRe = {"Reserved", "Not reserved"};

    public void reservationsStatus(){
        List<String> listStatusRe = new ArrayList<>();

        for(String data: statusRe){
            listStatusRe.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatusRe);
        reservation_status.setItems((listData));

    }

    // ORDERS PRODUCTNAME SELECTOR FROM DATABASE MENU

    public void ordersProductName(){

        String sql = "SELECT product_name FROM menu WHERE status = 'Available'";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while(result.next()){
                listData.add(result.getString("product_name"));
            }

            order_productName.setItems(listData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    // CHANGING ODER QUANTITY
    private SpinnerValueFactory<Integer> spinner;

    public void ordersSpinner(){

        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0);

        order_quantity.setValueFactory(spinner);
    }

    private int quantity;
    public void ordersQuantity(){
        quantity =  order_quantity.getValue();
    }

    // ADD ORDER

    public void ordersAdd() {
        showProductId();
        ordersCustomerID();
        orderTotal();

        String sql = "INSERT INTO orders" + "(user_id, customer_id, product_id, product_name, type, price, quantity, phone, address, date, relase, payment)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(order_productName.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {

                String orderType = "";
                double orderPrice = 0;

                String checkData = "SELECT * FROM menu WHERE product_name = '" + order_productName.getSelectionModel().getSelectedItem() + "'";

                statement = connection.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    orderType = result.getString("type");
                    orderPrice = result.getDouble("price");
                }

                prepared = connection.prepareStatement(sql);
                prepared.setString(1, String.valueOf(1));
                prepared.setString(2, String.valueOf(customerId));
                prepared.setString(3, String.valueOf(productId));
                prepared.setString(4, (String) order_productName.getSelectionModel().getSelectedItem());
                prepared.setString(5, orderType);

                double totalPrice = orderPrice * quantity;

                prepared.setString(6, String.valueOf(totalPrice));
                prepared.setString(7, String.valueOf(quantity));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepared.setString(8, String.valueOf(0));
                prepared.setString(9, "Not important");
                prepared.setString(10, String.valueOf(sqlDate));
                prepared.setString(11, "Not important");
                prepared.setString(12, "Not important");

                prepared.executeUpdate();

                orderDisplayTotal();
                orderDisplayData();

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

        String sql = "SELECT SUM(price) FROM orders WHERE customer_id = " + customerId;

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

    //PAYMENT AMOUNT FOR ORDER

    private double amount;
    private double balance;
    public void orderAmount(){
        orderTotal();

        Alert alert;

        if(orders_amount.getText().isEmpty() || orders_amount.getText() == null || orders_amount.getText() == ""){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Type the amount");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(orders_amount.getText());

            if(amount < totalP){
                orders_amount.setText("");
            }else {
                balance = (amount - totalP);
                order_balance.setText(String.valueOf(balance) + "zł");
            }
        }

    }

    //PAYMANT FOR ORDER BUTTON

    public void orderPay(){

        String sql = "INSERT INTO product (id, total, date) VALUES(?,?,?)";

        connection = Database.connectDb();

        try{

            Alert alert;

            if(balance == 0 || String.valueOf(balance) == "0.0zl" || String.valueOf(balance) == null
                    || totalP == 0 || String.valueOf(totalP) == "0.0zl" || String.valueOf(totalP) == "0.0zl"){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid, enter please! :D");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure about this?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    prepared = connection.prepareStatement(sql);
                    prepared.setString(1, String.valueOf(customerId));
                    prepared.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepared.setString(3, String.valueOf(sqlDate));

                    prepared.executeUpdate();

                    order_total.setText("0.0zl");
                    order_balance.setText("0.0zl");
                    orders_amount.setText("");

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Already done ;D");
                    alert.showAndWait();

                    orderDisplayData();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // DISPLAYING TOTAL PIRCE IN APP

    public void orderDisplayTotal(){
        orderTotal();
        order_total.setText(String.valueOf(totalP) + "zł");
    }

    // ADDING ORDER LIST TO DATABASE

        public ObservableList<OrdersUser> ordersList(){

        ordersCustomerID();

        ObservableList<OrdersUser> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders WHERE customer_id = " + customerId;

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();

            OrdersUser order;

            while(result.next()){
                order = new OrdersUser(result.getInt("id"),
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

                listData.add(order);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return listData;
        }

        //ORDER RECEIPT



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

                    orders_amount.setText("");
                    order_balance.setText("0.0zł");
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

        //ORDER SELECTOR DATA FORM TABLE

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
            ordersData = ordersList();

            orders_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            orders_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
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

    //CUSTOMERS

    //DISPLAYING ORDERS DATA USERS

    private ObservableList<OrdersUser> customersData;
    public void customerUserDisplayData(){
        customersData = customersUserList();

        customers_col_userID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        customers_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        customers_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        customers_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customers_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        customers_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        customers_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customers_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customers_col_customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customers_col_relase.setCellValueFactory(new PropertyValueFactory<>("relase"));

        customers_tableView.setItems(customersData);
    }

    // ADDING ORDER USER LIST TO DATABASE

    public ObservableList<OrdersUser> customersUserList(){

        ordersCustomerID();

        ObservableList<OrdersUser> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders WHERE CURRENT_DATE and payment ='done'";

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

    //UPDATE STATUS

    public void statusUpdate(){
        String sql = "UPDATE orders SET relase = '"+ customers_relase.getSelectionModel().getSelectedItem() +
                "' WHERE customer_id = '"+ customers_relaseField.getText() +"'";

        connection = Database.connectDb();

        try{

            statement = connection.createStatement();

            Alert alert;

            if(customers_relaseField.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill blanks");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to update product " + menu_productName.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Update is done");
                    alert.showAndWait();

                    if(customers_relase.getSelectionModel().getSelectedItem() == "Closed"){
                        statement = connection.createStatement();
                        statement.executeUpdate(sql);

                        String removeData = "DELETE FROM orders WHERE relase = 'Closed'";

                        statement = connection.createStatement();
                        statement.executeUpdate(removeData);
                    }


                    statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    //SHOW DATA AFTER ADD
                    customerUserDisplayData();

                }else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Update is cancelled");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // STATUS SELECTOR

    private String[] statusReleace = {"In process", "Ready for dispatch", "Dispatched", "Delivered", "Closed"};

    public void statusStatus(){
        List<String> listTypes = new ArrayList<>();

        for(String data: statusReleace){
            listTypes.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listTypes);
        customers_relase.setItems((listData));
    }

// SELECT FROM TABLE CUSTOMERS

    public void customersSelect(){

        OrdersUser orderuser = customers_tableView.getSelectionModel().getSelectedItem();

        int i = customers_tableView.getSelectionModel().getSelectedIndex();

        if((i - 1) < - 1){
            return;
        }

        customers_relaseField.setText(String.valueOf(orderuser.getCustomerId()));
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
    //SEARCH TABLE

    public void reservationsSearch() {
        FilteredList<Tables> filter = new FilteredList<>(reservationList, e -> true);

        reservations_serach.textProperty().addListener((obervable, newvalue, oldvalue) -> {
            filter.setPredicate(precidateReservations -> {

                if (newvalue.isEmpty() || newvalue == null) {
                    return true;
                }

                String searchKey = newvalue.toLowerCase();

                if (precidateReservations.getTableNumber().toString().contains(searchKey)) {
                    return true;
                } else if (precidateReservations.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (precidateReservations.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {

                    return false;
                }
            });
        });

        SortedList<Tables> sortedList = new SortedList<>(filter);

        sortedList.comparatorProperty().bind(reservations_tableView.comparatorProperty());

        reservations_tableView.setItems(sortedList);

    }


        // SWITCHING VISIBILITY WITH BUTTONS FOR FORMS

    public void switchForm(ActionEvent event){
        if(event.getSource() == dashboard_btn){
            dashborad_form.setVisible(true);
            menu_form.setVisible(false);
            reservations_form.setVisible(false);
            orders_form.setVisible(false);
            customers_form.setVisible(false);

            dashboardAT();
            dashboardET();
            dashboardCT();
            dashboardNOOC();
            dashboardIC();

            dashboard_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle(".nav-btn");
            customers_btn.setStyle(".nav-btn");
        }else if(event.getSource() == menu_btn){
            dashborad_form.setVisible(false);
            menu_form.setVisible(true);
            reservations_form.setVisible(false);
            orders_form.setVisible(false);
            customers_form.setVisible(false);

            menuShow();
            menuSearch();


            dashboard_btn.setStyle(".nav-btn");
            menu_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle(".nav-btn");
            customers_btn.setStyle(".nav-btn");
        }else if(event.getSource() == reservations_btn){
            dashborad_form.setVisible(false);
            menu_form.setVisible(false);
            reservations_form.setVisible(true);
            orders_form.setVisible(false);
            customers_form.setVisible(false);

            reservationsSearch();
            reservationShow();

            dashboard_btn.setStyle(".nav-btn");
            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            orders_btn.setStyle(".nav-btn");
            customers_btn.setStyle(".nav-btn");
        } else if(event.getSource() == orders_btn){
            dashborad_form.setVisible(false);
            menu_form.setVisible(false);
            reservations_form.setVisible(false);
            orders_form.setVisible(true);
            customers_form.setVisible(false);

            ordersProductName();
            ordersSpinner();
            orderDisplayData();
            orderDisplayTotal();

            dashboard_btn.setStyle(".nav-btn");
            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
            customers_btn.setStyle(".nav-btn");
        }else if(event.getSource() == customers_btn){
            dashborad_form.setVisible(false);
            menu_form.setVisible(false);
            reservations_form.setVisible(false);
            orders_form.setVisible(false);
            customers_form.setVisible(true);

            customerUserDisplayData();

            dashboard_btn.setStyle(".nav-btn");
            menu_btn.setStyle(".nav-btn");
            reservations_btn.setStyle(".nav-btn");
            orders_btn.setStyle(".nav-btn");
            customers_btn.setStyle("-fx-background-color: #fff;\n" +
                    "    -fx-background-radius: 5px 0px 0px 25px;");
        }
    }
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
        nav_username.setText(user);
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
        menuStatus();
        menuTypes();

        menuShow();
        menuClear();
        menuSearch();

        reservationsStatus();
        reservationsTypes();
        reservationShow();
        reservationsSearch();

        ordersProductName();
        ordersSpinner();
        orderDisplayData();
        orderDisplayTotal();

        dashboardAT();
        dashboardET();
        dashboardCT();
        dashboardNOOC();
        dashboardIC();

        customerUserDisplayData();
        statusStatus();
    }
}
