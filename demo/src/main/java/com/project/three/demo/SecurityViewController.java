package com.project.three.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SecurityViewController {
    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private Label securityQuestion;
    @FXML private TextField securityAnswer;
    @FXML private Button cancelButton;
    @FXML private Button verifyButton;

    @FXML private Button displayMode;

    /**
     * Initialization Method for security-view page load
     */
    public void initialize(){

        securityQuestion.setText(HelloApplication.getUser().getSecurityQuestion());
        cancelButton.setOnAction(event ->{
            try{
                new Notification("alert", "Canceling login");
                Views.loginView(rootPane);
                //display login statement
            }catch(Exception e){
                new Notification("fail", "Failed to load login page");
                System.out.println("Failed to load user login page: "+ e.getMessage());
            }
        });
        verifyButton.setOnAction(event ->{
            if(HelloApplication.getUser().verifySecurityAnswer(this.securityAnswer.getText())){
                new Notification("alert", "Successfully logged in");
                try{
                    Views.accountView(rootPane);
                    //display login fail statement
                }catch(Exception e){
                    new Notification("fail", "Failed to load account page");
                    System.out.println("Failed to load account page: "+ e.getMessage());
                    //display error
                }
            }else{
                new Notification("error", "Failed verification");
                try{
                    Views.loginView(rootPane);
                    //display login statement
                }catch(Exception e){
                    new Notification("fail", "Failed to load login page");
                    System.out.println("Failed to load user login page: "+ e.getMessage());
                }
            }
        });
        HelloApplication.addDisplayModeListener(rootPane, displayMode);

    }
}
