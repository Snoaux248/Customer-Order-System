package com.project.three.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;


public class AccountViewController{

    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private Label greeting;

    @FXML private Button displayMode;
    @FXML private Button viewOrders;
    @FXML private Button newOrder;
    @FXML private Button logout;

    /**
     * Initialization Method for account-view page load
     */
    public void initialize(){
        if(HelloApplication.getUser().getAdministratorStatus()){
            greeting.setText("Welcome  Administrator!");
            grid.add(new Label("ID\t\t\t Locked\t Admin\t Password\t SecurityQuestion\t SecurityAnswer\t Name\t Address\t Card Number\t Balance\t Orders"),0, 1);
            int i = 1;
            for(Account user : HelloApplication.getSystemAccounts()) {
                i++;
                Label userInfo = new Label(user.toString());
                if (!user.getAdministratorStatus()) {
                    userInfo.setText(userInfo.getText() + " " + user.getBankAccount().toString() + " " + user.getUserOrders().size());
                }
                grid.add(userInfo, 0, i);
                if (!user.getAdministratorStatus()) {
                    userInfo.setOnMouseClicked(event -> {
                        HelloApplication.orders = user.getUserOrders();
                        greeting.setText("Selected " + user.getBankAccount().getCardHolder() + " (" + user.getUserOrders().size() + " Orders)");
                        viewOrders.setOnAction(event2 -> {

                            try {
                                Views.pastOrdersView(rootPane);
                            } catch (IOException e) {
                                System.out.println("Failed to past orders page: " + e.getMessage());
                            }
                        });
                    });
                }
                System.out.print("\n");
            }
            newOrder.setText("Select a users below");
        }else{
            greeting.setText("Welcome " + HelloApplication.getUser().getBankAccount().getCardHolder() + "!");
            newOrder.setOnAction(event -> {
                try {
                    Views.newOrderView(rootPane);
                } catch (IOException e) {
                    System.out.println("Failed to load new order page: " + e.getMessage());
                }
            });
            viewOrders.setOnAction(event -> {
                try {
                    Views.pastOrdersView(rootPane);
                } catch (IOException e) {
                    System.out.println("Failed to past orders page: " + e.getMessage());
                }
            });
        }
        logout.setOnAction(event ->{
            try {
                new Notification("alert", "Successfully logged out");
                Files.saveUsers(HelloApplication.getSystemAccounts());
                Views.loginView(rootPane);
            }catch (IOException e){
                new Notification("fail", "Failed to load login page");
                System.out.println("Failed to load user login page: "+ e.getMessage());
            }
        });
        HelloApplication.addDisplayModeListener(rootPane, displayMode);

    }


}
