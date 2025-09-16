package com.project.three.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HelloApplication extends Application{
    private static String displayMode = "Dark";

    private static Account user;
    public static ArrayList<Order> orders;
    public static Order adminOrder;
    private static Order order;
    private static ArrayList<Account> systemAccounts = new ArrayList<>();
    public static Stage primaryStage;
    public static boolean backPropagateOrder = false;

    /**
     * Application initialization Method for views
     * @param stage : Stage
     * @throws IOException : Exception
     */
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 720);
        primaryStage = stage;
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/dark.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * Main Method for app logic setup;
     * @param args : String[]
     */
    public static void main(String[] args) {
        systemAccounts = new ArrayList<>();
        Files.loadUsers(systemAccounts);
        System.out.println("ID\t\t\t Locked\t Admin\t Password\t SecurityQuestion\t SecurityAnswer\t Name\t Address\t Card Number\t Balance");
        for(Account account : systemAccounts){
            System.out.print(account.toString());
            if(!account.getAdministratorStatus()){
                System.out.print(account.getBankAccount().toString());
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        launch();
    }

    /**
     * Accessor Method for systemAccounts
     * @return : ArrayList<Account>
     */
    public static ArrayList<Account> getSystemAccounts(){
        return systemAccounts;
    }

    /**
     * Setter Method for current user
     * @param account : Account
     */
    public static void setUser(Account account){
        HelloApplication.user = account;
    }
    /**
     * Accessor Method for current user
     * @return : Account
     */
    public static Account getUser(){
        return user;
    }

    /**
     * Setter Method for current order
     * @param order : Order
     */
    public static void setOrder(Order order){
        HelloApplication.order = order;
    }
    /**
     * Accessor Method for current order
     * @return Order
     */
    public static Order getOrder(){
        return order;
    }
    /**
     *  Setter Method for display mode
     * @param displayMode : String
     */
    public static void setDisplayMode(String displayMode){
        HelloApplication.displayMode = displayMode;
    }
    /**
     * Accessor Method for display mode
     * @return : String
     */
    public static String getDisplayMode(){
        return HelloApplication.displayMode;
    }

    /**
     * Equivalence Method for display mode
     * @param displayMode : String
     * @return : boolean
     */
    public static boolean equalsDisplayMode(String displayMode){
        return HelloApplication.displayMode.equals(displayMode);
    }
    /**
     * Helper Method to update usr interface to dark mode
     * @param rootPane : AnchorPane
     */
    public static void darkMode(AnchorPane rootPane){
        Scene scene = rootPane.getScene();
        scene.getStylesheets().setAll(Objects.requireNonNull(HelloApplication.class.getResource("css/dark.css")).toExternalForm());

        Button displayButton = (Button)rootPane.getScene().lookup("#displayMode");
        displayButton.setText("Light Mode");
        setDisplayMode("Dark");
    }
    /**
     * Helper Method to update usr interface to light mode
     * @param rootPane : AnchorPane
     */
    public static void lightMode(AnchorPane rootPane){
        Scene scene = rootPane.getScene();
        scene.getStylesheets().setAll(Objects.requireNonNull(HelloApplication.class.getResource("css/light.css")).toExternalForm());

        Button displayButton = (Button)rootPane.getScene().lookup("#displayMode");
        displayButton.setText("Dark Mode");
        setDisplayMode("Light");
    }

    /**
     * Helper Method to set display mode button event listener on views
     * @param rootPane : AnchorPane
     * @param displayButton : Button
     */
    public static void addDisplayModeListener(AnchorPane rootPane, Button displayButton){
        displayButton.setOnAction(event ->{
            if(HelloApplication.equalsDisplayMode("Dark")) {
                new Notification("warning", "Light Mode Enabled");
                HelloApplication.lightMode(rootPane);
            }else if(HelloApplication.equalsDisplayMode("Light")){
                new Notification("warning", "Dark Mode Enabled");
                HelloApplication.darkMode(rootPane);
            }
        });
    }
}