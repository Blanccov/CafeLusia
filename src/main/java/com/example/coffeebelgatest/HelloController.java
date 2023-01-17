package com.example.coffeebelgatest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane sign_form;

    @FXML
    private Hyperlink signIn_createAccount;

    @FXML
    private AnchorPane signIn_form;

    @FXML
    private Button signIn_loginBtn;


    @FXML
    private PasswordField signIn_password;

    @FXML
    private TextField signIn_username;

    @FXML
    private Hyperlink signUp_alreadyHaveAccount;

    @FXML
    private TextField signUp_email;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private TextField signUp_username;

    private Connection connection;
    private PreparedStatement prepared;

    private Statement statement;
    private ResultSet result;

    //CHECKING IF THE EMAIL IS CORRECT
    public boolean checkEmail(){

        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");

        Matcher matcher = pattern.matcher(signUp_email.getText());

        if(matcher.find() && matcher.group().matches(signUp_email.getText())){
            return true;
        }else {


            return false;
        }
    }



    // SIGN UP FUNCTION

    public void signup(){


        String sql = "INSERT INTO user ( email, username, password) VALUES(?,?,?)";

        String sql_validation = "SELECT COUNT(username) FROM user WHERE username= '" + signUp_username.getText() + "'";

        connection = Database.connectDb();

        try{

            prepared = connection.prepareStatement(sql);
            prepared.setString(1, signUp_email.getText());
            prepared.setString(2, signUp_username.getText());
            prepared.setString(3, signUp_password.getText());

            Alert alert;

            if(signUp_email.getText().isEmpty() || signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Blank Fields");
                alert.showAndWait();

            }else if(signUp_password.getText().length() < 8){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password needs 8 characters ");
                alert.showAndWait();
            }else {
                int usernameCount = 0;
                if (checkEmail()) {
                        statement = connection.createStatement();
                        result = statement.executeQuery(sql_validation);
                        if(result.next()) {
                            usernameCount = result.getInt("COUNT(username)");
                        }
                    if(usernameCount !=0) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("This username was already exist");
                        alert.showAndWait();
                    }else{

                        prepared.execute();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Your account is already done! :D");
                    alert.showAndWait();

                    signUp_email.setText("");
                    signUp_username.setText("");
                    signUp_password.setText("");
                }} else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid email");
                    alert.showAndWait();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // USER ID FOR ORDERS


// LOGIN FUNCTION
    private double x = 0;
    private double y = 0;

    public void login() {

        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            prepared.setString(1, signIn_username.getText());
            prepared.setString(2, signIn_password.getText());

            result = prepared.executeQuery();

            Alert alert;
//            EMPTY FIELDS
            if(signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username or password field is empty!");
                alert.showAndWait();

            }else{
                if(result.next()){
//                    CORRECTLY
                    Data.username = signIn_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Nice! Hello in our Coffee :D");
                    alert.showAndWait();

                    signIn_loginBtn.getScene().getWindow().hide();

//                    ADMIN PANEL DASHBOARD

                    Parent root = FXMLLoader.load(getClass().getResource("adminpanel.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                }else {
//                    CHECK USERS ACCOUNTS
                    loginuser();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void loginuser() {

        String sql = "SELECT * FROM user WHERE username = ? and password = ?";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            prepared.setString(1, signIn_username.getText());
            prepared.setString(2, signIn_password.getText());

            result = prepared.executeQuery();

            Alert alert;
//            EMPTY FIELDS
            if(signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username or password field is empty!");
                alert.showAndWait();

            }else{
                if(result.next()){
//                    CORRECTLY
                    Data.username = signIn_username.getText();
                    userLoginID();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Nice! Hello in our Coffee :D");
                    alert.showAndWait();

                    signIn_loginBtn.getScene().getWindow().hide();

//                    USER PANEL DASHBOARD

                    Parent root = FXMLLoader.load(getClass().getResource("userpanel.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                }else {
//                    WRONG PSW OR USR

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password!");
                    alert.showAndWait();

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    int loginUserId;
    public void userLoginID(){
        String sql = "SELECT id FROM user WHERE username = '"+ signIn_username.getText() + "'";

        connection = Database.connectDb();

        try{
            prepared = connection.prepareStatement(sql);
            result = prepared.executeQuery();


            while(result.next()){
                loginUserId = result.getInt("id");
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // SWTICH FORM BETWEEN LOGIN AND SIGNUP

    public void switchForm(ActionEvent event){

        if(event.getSource() == signIn_createAccount){

            signIn_form.setVisible(false);
            signUp_form.setVisible(true);
        }else if(event.getSource() == signUp_alreadyHaveAccount){
            signIn_form.setVisible(true);
            signUp_form.setVisible(false);
        }
    }


    public void close(){
        System.exit(0);
    }
    public void minimize(){
        Stage stage = (Stage) sign_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}