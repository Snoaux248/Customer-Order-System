package com.project.three.demo;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDateTime;

public class Order{
    private int orderID;
    private boolean deliveryType;
    private String date;// 3$ for delivery, 0 instore pickup
    private ArrayList<Item> shoppingCart;
    /**
     * Constructor method for new orders
     */
    public Order(){
        setOrderID();
        setDeliveryType(false);
        this.shoppingCart = new ArrayList<>();
    }
    /**
     * Constructor method for file loaded orders
     * @param orderID : int
     * @param deliveryType : boolean
     * @param shoppingCart : ArrayList<Item>
     */
    public Order(int orderID, boolean deliveryType, String date, ArrayList<Item> shoppingCart){
        this.orderID = orderID;
        this.deliveryType = deliveryType;
        this.date = date;
        this.shoppingCart = shoppingCart;
    }
    /**
     * Mutator method for order ID
     */
    public void setOrderID(){
        Random rand = new Random();
        orderID = rand.nextInt(10000);
    }
    /**
     * Mutator method for delivery type
     */
    public void setDeliveryType(boolean delivery){
        this.deliveryType = delivery;
    }
    /**
     * Modifier method for shpooing cart
     * @param item : Item
     */
    public void addItem(Item item){
        this.shoppingCart.add(item);
    }
    /**
     * Setter method for date
     */
    public void setDate(){
        String date = LocalDateTime.now().toString();
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
        } catch (DateTimeParseException e1) {
            dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        }
        this.date = dateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a"));
    }
    /**
     * Accessor method for orderID
     * @return : int
     */
    public int getOrderID(){
        return this.orderID;
    }
    /**
     * Accessor method for delivery type
     * @return : boolean
     */
    public boolean getDeliveryType(){
        return this.deliveryType;
    }
    /**
     * Accessor method for shopping cart
     * @return : ArrayList<Item>
     */
    public ArrayList<Item> getShoppingCart(){
        return this.shoppingCart;
    }
    /**
     * accessor method for date
     * @return String
     */
    public String getDate(){
        return this.date;

    }
    /**
     * Helper method to calculate delivery fee
     * @return : double
     */
    public double getDeliveryFee(){
        return getDeliveryType() ? 3.0: 0.0;
    }
    /**
     * Helper method to calculate delivery tax
     * @return : double
     */
    public double getTotalTax(){
        double tax = 0.0;
        for(Item item: shoppingCart){
            tax += item.getPrice() * item.getQuantity() * 0.0125;
        }
        return tax;
    }
    /**
     * Helper method to calculate total discount of order
     * @return : double
     */
    public double getDiscount(){
        double discount = 0.0;
        for(Item item: shoppingCart){
            discount += item.getPrice() * item.getQuantity() * (item.getDiscount()/100);
        }
        return discount;
    }
    /**
     * Helper method to calculate total price of order before tax and discount
     * @return : double
     */
    public double getPrice(){
        double price = this.getDeliveryFee();
        for(Item item: shoppingCart){
            price += item.getPrice() * item.getQuantity();
        }
        return price;
    }
    /**
     * Helper method to calculate total price of order after tax and discount
     * @return : double
     */
    public double calculateTotal(){
        return this.getDeliveryFee() + this.getPrice() - this.getDiscount() + this.getTotalTax();
    }
    /**
     * Display method for viewing order details
     * @return : String
     */
    public String toString(){
        String temp = "";
        temp +=           "OrderID: "+ this.getOrderID() +
                "\nDelivery method: "+ (this.getDeliveryType() ? "Delivery": "Pickup") +
                "\nDate: "+ this.getDate() + "\n";
        for(Item item: this.shoppingCart){
            temp += item.toString();
        }
        temp += "TotalPrice: "+ String.format("$%,.2f", this.calculateTotal());
        return temp;
    }
    /**
     * Storage method to get data to write to file
     * @return : String
     */
    public String toFile(){
        String temp = orderID +"\n"+
                      deliveryType +"\n"+
                getShoppingCart().size() +"\n"+
                getDate() +"\n";
        for(Item item: this.shoppingCart){
            temp += item.toFile();
        }
        return temp;
    }
}