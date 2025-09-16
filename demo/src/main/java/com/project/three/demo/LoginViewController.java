package com.project.three.demo;

import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class LoginViewController {

    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;
    @FXML private ScrollPane notificationView;

    @FXML private TextField user;
    @FXML private PasswordField pass;
    @FXML private TextField shownPass = new TextField();
    @FXML private CheckBox showPass;

    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML private Button displayMode;

    private int lastUserID = 0;
    private int failedLogins = 0;

    /**
     * Initialization Method for login-view page load
     */
    public void initialize(){

        loginButton.setOnAction(event ->{

            final String password;
            if(!pass.getText().isEmpty()){
                password = pass.getText();
            }else{
                password = shownPass.getText();
            }
            Account tempUser = null;
            final int customerID;
            try{
                customerID = Integer.parseInt(user.getText());
            }catch(Exception e){
                new Notification("error", "Invalid ID formatting");
                System.out.println("Error: "+ e.getMessage());
                return;
            }
            for(Account account : HelloApplication.getSystemAccounts()){
                if(account.verifyCustomerID(customerID)){
                    tempUser = account;
                }
            }
            if(tempUser == null){
                //Show Error could not find user account
                return;
            }else if(tempUser.isLocked()){
                new Notification("error", "Account is locked ");
                //Show Error Account is locked due to failed verification!
                return;
            };
            if(customerID != lastUserID){
                lastUserID = customerID;
                failedLogins = 0;
            }
            if(!tempUser.verifyPassword(password)){
                failedLogins++;
                new Notification("error", "Incorrect username or password");
                if(failedLogins >= 3){
                    new Notification("error", "Account has been locked due to failed verification");
                    tempUser.lock();
                }
            }else if(tempUser.verifyPassword(password)){
                HelloApplication.setUser(tempUser);
                new Notification("alert", "Verification");
                try{
                    Views.securityView(rootPane);
                }catch(Exception e){
                    new Notification("fail", "Failed to load account page");
                    System.out.println("Failed to load verify page: "+ e.getMessage());
                }
            }

        });
        registerButton.setOnAction(event ->{
            try{
                Views.registerView(rootPane);
            }catch(Exception e){
                new Notification("fail", "Failed to load registration page");
                System.out.println("Failed to load user registration page: "+ e.getMessage());
            }
        });
        showPass.setOnAction(event ->{
            if(this.showPass.isSelected()){
                new Notification("warning", "Displaying password");
                Parent parent = pass.getParent();
                int index = ((Pane) parent).getChildren().indexOf(pass);
                shownPass.setText(pass.getText());
                pass.setText("");
                ((Pane) parent).getChildren().remove(index);
                ((Pane) parent).getChildren().add(index, shownPass);
            }else{
                Parent parent = shownPass.getParent();
                int index = ((Pane) parent).getChildren().indexOf(shownPass);
                pass.setText(shownPass.getText());
                shownPass.setText("");
                ((Pane) parent).getChildren().remove(index);
                ((Pane) parent).getChildren().add(index, pass);
            }
        });
        HelloApplication.addDisplayModeListener(rootPane, displayMode);
    }

}