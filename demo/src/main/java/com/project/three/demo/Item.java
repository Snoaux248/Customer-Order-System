package com.project.three.demo;

public class Item{
    private final String itemName;
    private final String itemDescription;
    private final double price;
    private final double discount;
    private int quantity;

    /**
     * Constructor method for Catalogue viewing
     * @param itemName : String
     * @param itemDescription : String
     * @param price : double
     * @param discount : double
     */
    public Item(String itemName, String itemDescription, double price, double discount) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.discount = discount;
        this.quantity = 0;
    }
    /**
     * Constructor method for new item selection
     * @param item : Item
     * @param quantity : int
     */
    public Item(Item item, int quantity) {
        this.itemName = item.itemName;
        this.itemDescription = item.itemDescription;
        this.price = item.price;
        this.discount = item.discount;
        this.quantity = quantity;
    }
    /**
     * Constructor method for file loaded Items
     * @param itemName : String
     * @param itemDescription : String
     * @param price : double
     * @param discount : double
     * @param quantity : int
     */
    public Item(String itemName, String itemDescription, double price, double discount, int quantity) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }
    /**
     * Accessor method for item price
     * @return : double
     */
    public double getPrice(){
        return this.price;
    }
    /**
     * Accessor method for item discount
     * @return : double
     */
    public double getDiscount(){
        return this.discount;
    }
    /**
     * Accessor method for item quantity
     * @return : double
     */
    public int getQuantity(){
        return this.quantity;
    }
    /**
     * Accessor Method for item name
     * @return : String
     */
    public String getItemName(){
        return this.itemName;
    }
    /**
     * Display method for view item details
     * @return : String
     */
    public String toString(){
        String display = "Name: "+ this.itemName +
                "\nDescription: "+ this.itemDescription +
                      "\nPrice: "+ String.format("$%,.2f", this.price)  +
                       "\nSale: "+ this.discount +
                "%\nSale Price: "+ String.format("$%,.2f", this.price * (1 - this.discount/100)) + '\n';

        if(this.quantity != 0){
            return display + "Quantity: "+ this.quantity +'\n';
        }
        return display;
    }
    /**
     * Storage method to get data to write to file
     * @return : String
     */
    public String toFile(){
        return itemName +"\n"+
                itemDescription +"\n"+
                price +"\n"+
                discount +"\n"+
                quantity+"\n";
    }
}