package com.project.three.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Random;


public class RegisterViewController {

    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private TextField prefUser;
    @FXML private PasswordField prefPass;
    @FXML private PasswordField prefPassConf;
    @FXML private TextField securityQuestion;
    @FXML private TextField securityAnswer;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField address;
    @FXML private TextField card;


    @FXML private Button displayMode;
    @FXML private Button cancelButton;
    @FXML private Button registerButton;

    /**
     * Initialization Method for register-view page load
     */
    public void initialize(){

        registerButton.setOnAction(event ->{
            ArrayList<Account> systemAccounts = HelloApplication.getSystemAccounts();
            Account newUser = new Account(false);
            boolean[] verification = {false, false, false, false, false, false, false, false, false};
            verification[0] = newUser.setCustomerID(prefUser.getText(), systemAccounts);
            verification[1] = newUser.setPassword(prefPass.getText().trim());
            verification[2] = newUser.verifyPassword(prefPassConf.getText().trim());
            if(!verification[2]){
                new Notification("error", "Passwords don't match");
            }
            verification[3] = newUser.setSecurityQuestion(securityQuestion.getText());
            verification[4] = newUser.setSecurityAnswer(securityAnswer.getText());
            BankAccount newBankAccount = new BankAccount();
            verification[5] = newBankAccount.changeCardHolder(firstName.getText().trim() +" "+ lastName.getText().trim());
            verification[6] = newBankAccount.changeAddress(address.getText().trim());
            verification[7] = newBankAccount.changeCardNumber(card.getText().trim());
            System.out.print("\n");
            newUser.setBankAccount(newBankAccount);
            verification[8] = true;
            for(int i = 0; i < verification.length -1; i++){
                System.out.print(verification[i] + " ");
                if(!verification[i]){
                    verification[8] = false;
                    break;
                }
            }
            System.out.print("\n");
            //load notifications;
            if(verification[8]){
                systemAccounts.add(newUser);
                Random rand = new Random();
                double startingBalance = rand.nextInt(10000) - 2000;
                startingBalance = startingBalance < 0 ? 0: startingBalance;
                System.out.println("Starting Balance: " + startingBalance);
                newBankAccount.deposit(startingBalance);
                new Notification("balance", String.format("$%,.2f",  newBankAccount.getBalance()));
                new Notification("alert", "Welcome to MustafaCart");
                for(Account account : HelloApplication.getSystemAccounts()){
                    System.out.print(account.toString());
                    if(!account.getAdministratorStatus()){
                        System.out.print(account.getBankAccount().toString());
                    }
                    System.out.print("\n");
                }
                Files.saveUsers(systemAccounts);
                try{
                    Views.loginView(rootPane);
                }catch(Exception e){
                    new Notification("fail", "Failed to load registration page");
                    System.out.println("Failed to load user registration page: "+ e.getMessage());
                }
            }
        });
        cancelButton.setOnAction(event ->{
            try{
                Views.loginView(rootPane);
            }catch(Exception e){
                new Notification("fail", "Failed to load login page");
                System.out.println("Failed to load user login page: "+ e.getMessage());
            }
        });
        HelloApplication.addDisplayModeListener(rootPane, displayMode);

    }
}
