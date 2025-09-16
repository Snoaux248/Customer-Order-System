package com.project.three.demo;

import java.util.ArrayList;
import java.util.Date;

public class Account{
    boolean administrator;
    long timeOfLock = 0;

    private int customerID;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private ArrayList<Order> userOrders = new ArrayList<>();
    private BankAccount bankAccount;

    /**
     * Constructor for new accounts
     * @param administrator : boolean
     */
    public Account(boolean administrator){
        customerID = 0;
        password = "";
        securityQuestion = "";
        securityAnswer = "";
        this.administrator = administrator;
        if(!administrator){
            BankAccount bankAccount = new BankAccount();
        }
    }
    /**
     * Constructor for file loaded accounts
     * @param administrator : boolean
     * @param customerID : int
     * @param password : String
     * @param securityQuestion : String
     * @param securityAnswer : String
     * @param bankAccount : BankAccount
     * @param orders : Order
     */
    public Account(boolean administrator,
                   int customerID,
                   String password,
                   String securityQuestion,
                   String securityAnswer,
                   BankAccount bankAccount,
                   ArrayList<Order> orders){
        this.administrator = administrator;
        this.customerID = customerID;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.bankAccount = bankAccount;
        this.userOrders = orders;
    }

    /**
     * Mutator method for Customer ID
     * @param customerID : int
     * @param systemAccounts : ArrayList<Account>
     * @return : boolean
     */
    public boolean setCustomerID(String customerID, ArrayList<Account> systemAccounts){
        try{
            Integer.parseInt(customerID.trim());
        }catch(Exception e){
            new Notification("error", "Invalid ID: Not and int");
            System.out.println("Error not an integer:"+ e.getMessage());
            return false;
        }
        if(customerID.length() != 8){
            new Notification("error", "Invalid ID: Needs 8 Digits");
            System.out.println("Id Must be 8 characters long:");
            return false;
        }
        for(Account account : systemAccounts){
            if(account.verifyCustomerID(Integer.parseInt(customerID.trim()))){
                new Notification("error", "Invalid ID: Already taken");
                System.out.println("The id " + String.format("%08d", Integer.parseInt(customerID.trim())) + " is already taken");
                return false;
            }
        }
        this.customerID = Integer.parseInt(customerID.trim());
        return true;
    }
    /**
     * Mutator method for account password
     * @param password : String
     * @return : boolean
     */
    public boolean setPassword(String password){
        this.password = password;
        int[] counter = characterTest(password);
        if(password.length() < 6 || counter[4] > 2 || counter[0] == 0 || counter[2] == 0 || counter[3] == 0){
            System.out.println("Poor Security:\nAt-least 6 characters\nAt-least 1 Capital Character\nAt-least 1 number\nAt-least 1 special character '@', '#', '$', '%', '&', '*'");
            if(password.length() < 6){
                new Notification("error", "Bad Password: Must be 6 characters");
            }
            if(counter[4] > 2 || counter[0] == 0){
                new Notification("error", "Bad Password: Must contain one capital");
            }
            if(counter[2] == 0){
                new Notification("error", "Bad Password: Must contain one digit");
            }
            if(counter[3] == 0){
                new Notification("error", "Bad Password: Must contain one special @#$%&*");
            }
            return false;
        }
        this.password = password;
        return true;
    }
    /**
     * Mutator method for account security question
     * @param securityQuestion : String
     * @return : boolean
     */
    public boolean setSecurityQuestion(String securityQuestion){
        if(securityQuestion.trim().isEmpty()){
            new Notification("error", "Security Q: is blank");
            return false;
        }
        this.securityQuestion = securityQuestion;
        return true;
    }
    /**
     * Mutator method for account security answer
     * @param securityAnswer : String
     * @return : boolean
     */
    public boolean setSecurityAnswer(String securityAnswer){
        if(securityAnswer.trim().isEmpty()){
            new Notification("error", "Security A: is blank");
            return false;
        }
        this.securityAnswer = securityAnswer;
        return true;
    }
    /**
     * Mutator method for account bank account
     * @param bankAccount : BankAccount
     */
    public void setBankAccount(BankAccount bankAccount){
        this.bankAccount = bankAccount;
    }
    /**
     * Equivalence method for customer id
     * @param customerID : int
     * @return : boolean
     */
    public boolean verifyCustomerID(int customerID){
        return this.customerID == customerID;
    }
    /**
     * Equivalence method for password
     * @param password : String
     * @return : boolean
     */
    public boolean verifyPassword(String password){
        return this.password.equals(password);
    }
    /**
     * Accessor method for account security question
     * @return : String
     */
    public String getSecurityQuestion(){
        return this.securityQuestion;
    }
    /**
     * Equivalence method for account security answer
     * @param securityAnswer : String
     * @return : boolean
     */
    public boolean verifySecurityAnswer(String securityAnswer){
        return this.securityAnswer.equals(securityAnswer);
    }
    /**
     * Accessor Method for yser orders
     * @return : ArrayList<Order>
     */
    public ArrayList<Order> getUserOrders(){
        return this.userOrders;
    }
    /**
     * Accessor user bank account
     * @return : BankAccount
     */
    public BankAccount getBankAccount(){
        return this.bankAccount;
    }
    /**
     * Accessor method for admin status
     * @return : boolean
     */
    public boolean getAdministratorStatus(){
        return this.administrator;
    }
    /**
     * Setter Method for account locking
     */
    public void lock(){
        Date now = new Date();
        timeOfLock = now.getTime();
    }
    /**
     * Equivalence/Accessor method for account locking
     * @return : Boolean
     */
    public boolean isLocked(){
        Date now = new Date();
        if(timeOfLock == 0){
            return false;
        }
        if(now.getTime() - timeOfLock < 10*60*1000){ //10 minutes
            return true;
        }
        timeOfLock = 0;
        return true;
    }
    /**
     * Display method for administrator viewing
     * @return : String
     */
    public String toString(){
        return this.customerID +"\t "+ this.isLocked() +"\t "+ this.getAdministratorStatus() +"\t "+ this.password +"\t "+ this.securityQuestion +"\t "+ this.securityAnswer;
    }
    /**
     * Storage Method to get data to write to file
     * @return : String
     */
    public String toFile(){
        String temp;
        if(administrator) {
            temp =    "\n"+ administrator +"\n"+
                    customerID +"\n"+
                    password +"\n"+
                    securityQuestion +"\n"+
                    securityAnswer +"\n";
        }else{
            temp =    "\n"+ administrator +"\n"+
                    customerID +"\n"+
                    password +"\n"+
                    securityQuestion +"\n"+
                    securityAnswer +"\n"+
                    bankAccount.toFile() +"\n"+
                    userOrders.size() +"\n";
            for (Order order : userOrders) {
                temp += order.toFile();
            }
        }
        return temp;
    }
    /**
     * Helper method for character identification
     * @param s : String
     * @return : int[]
     */
    private int[] characterTest(String s){
        int[] counter = {0, 0, 0, 0, 0};
        char[] special = {'@', '#', '$', '%', '&', '*'};
        for(char c : s.toCharArray()){
            if(Character.isUpperCase(c)){
                counter[0]++;
            }else if(Character.isAlphabetic(c)){
                counter[1]++;
            }else if(Character.isDigit(c)){
                counter[2]++;
            }else{
                for (char value : special) {
                    if (value == c) {
                        counter[3]++;
                        break;
                    }
                }
            }
        }
        return counter;
    }
}