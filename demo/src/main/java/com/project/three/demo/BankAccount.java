package com.project.three.demo;

import java.util.ArrayList;
import java.util.Random;

public class BankAccount{
    private String cardHolder;
    private String address;
    private String cardNumber;
    private double balance;
    public ArrayList<Integer> authNumber = new ArrayList<>();

    /**
     * Constructor Method for new bank accounts
     */
    public BankAccount(){
        this.cardHolder = "";
        this.address = "";
        this.cardNumber = "";
        this.balance = 0;
    }
    /**
     * Constructor method for file loaded bank accounts
     * @param customerName : String
     * @param address : String
     * @param cardNumber : String
     * @param balance : double
     * @param authNumber : ArrayList<Integer>
     */
    public BankAccount(String customerName, String address, String cardNumber, double balance, ArrayList<Integer> authNumber){
        this.cardHolder = customerName;
        this.address = address;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.authNumber = authNumber;
    }
    /**
     * Modifier Method for balance +=
     * @param amount : double
     */
    public void deposit(double amount){
        this.balance += amount;
    }
    /**
     * Modifier method for balance -=
     * @param amount : double
     */
    public void withdraw(double amount){
        this.balance -= amount;
    }
    /**
     * Modifier method for balance based with fund check
     * @param amount : double
     * @return : boolean
     */
    public boolean charge(double amount){
        if(this.balance < amount){
            return false;
        }else{
            withdraw(amount);
        }
        return true;
    }
    /**
     * Accessor method for card holder
     * @return : String
     */
    public String getCardHolder(){
        return this.cardHolder;
    }
    /**
     * Helper Accessor method for address
     * @return : String
     */
    private String getAddress(){
        return this.address;
    }
    /**
     * Helper Accessor method for address
     * @return : String
     */
    private String getCardNumber(){
        return this.cardNumber;
    }
    /**
     * Accessor method for balance
     * @return : double
     */
    public double getBalance(){
        return this.balance;
    }
    /**
     * Mutator method for card holder
     * @param newName : String
     * @return : boolean
     */
    public boolean changeCardHolder(String newName){
        this.cardHolder = newName;
        if(newName.trim().isEmpty()){
            new Notification("error", "Card Holder: Cannot be empty");
        }
        return !newName.trim().isEmpty();
    }
    /**
     * Mutator method for address
     * @param newAddress : String
     * @return : boolean
     */
    public boolean changeAddress(String newAddress){
        this.address = newAddress;
        if(newAddress.trim().isEmpty()){
            new Notification("error", "Address: Cannot be empty");
        }
        return !newAddress.trim().isEmpty();
    }
    /**
     * Mutator method for card holder
     * @param newCardNumber : String
     * @return : boolean
     */
    public boolean changeCardNumber(String newCardNumber){
        this.cardNumber = newCardNumber.trim();
        if(!newCardNumber.matches("\\d+")){
            new Notification("error", "Card Number: Must be 16 digits");
            System.out.println("Error does not contain only digits");
            return false;
        }
        if(!(newCardNumber.trim().length() == 16)){
            new Notification("error", "Card Number: Must be of length 16");
            return false;
        }
        return true;
    }
    /**
     *  Modifier method for auth numbers
     * @return : int
     */
    public int generateNewAuthNumber(){
        Random rand = new Random();
        int newAuth = rand.nextInt(10000);
        this.authNumber.add(newAuth);
        return newAuth;
    }
    /**
     * Display method for administrator viewing
     * @return : String
     */
    public String toString(){
        return "\t"+ this.getCardHolder()
                +"\t"+ this.getAddress()
                +"\t"+ this.getCardNumber()
                +"\t"+ String.format("$%,.2f", this.getBalance());
    }
    /**
     * Storage method to get data to write to file
     * @return : String
     */
    public String toFile(){
        String temp = cardHolder +"\n"+
                address +"\n"+
                cardNumber +"\n"+
                balance +"\n"+
                authNumber.size();
        for(int i : authNumber){
            temp += "\n"+ i;
        }
        return temp;
    }
}