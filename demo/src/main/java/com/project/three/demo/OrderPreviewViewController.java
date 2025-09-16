package com.project.three.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class OrderPreviewViewController{

    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private Button displayMode;
    @FXML private Button backButton;
    @FXML private Button confirmOrder;
    @FXML private CheckBox delivery;

    @FXML private Label price;
    @FXML private Label fee;
    @FXML private Label discount;
    @FXML private Label tax;
    @FXML private Label total;

    private int columns;

    /**
     * Initialization Method for order-preview-view page load
     */
    public void initialize() {
        //350: adjustment for total pane, 300 width of item pane
        columns = (int) (HelloApplication.primaryStage.getWidth()-350) / 300;
        columns = columns == 0 ? 1: columns;
        HelloApplication.primaryStage.widthProperty().addListener(event -> {
            //350: adjustment for total pane, 300 width of item pane
            int columnsTemp = (int) (HelloApplication.primaryStage.getWidth()-350) / 300;
            if(columnsTemp != columns){
                columns = columnsTemp == 0 ? 1: columnsTemp;
                setupGrid();
            }
        });

        price.setText(String.format("$%,.2f", HelloApplication.getOrder().getPrice()));
        fee.setText(String.format("$%,.2f", HelloApplication.getOrder().getDeliveryFee()));
        discount.setText(String.format("$%,.2f", HelloApplication.getOrder().getDiscount()));
        tax.setText(String.format("$%,.2f", HelloApplication.getOrder().getTotalTax()));
        total.setText(String.format("$%,.2f", HelloApplication.getOrder().calculateTotal()));

        backButton.setOnAction(event -> {
            try{
                Views.backToOrderView(rootPane);
            }catch(Exception e){
                new Notification("fail", "Failed to load new order page");
                System.out.println("Error loading new Order view"+ e.getMessage());
            }
        });
        confirmOrder.setOnAction(event -> {
            if(HelloApplication.getOrder().getShoppingCart().isEmpty()){
                System.out.println("Shopping cart is empty");
                new Notification("alert", "Cart is empty");
                return;
            }
           Account user = HelloApplication.getUser();
           boolean charge = user.getBankAccount().charge(HelloApplication.getOrder().calculateTotal());
           if(charge) {
               new Notification("alert", "Successfully placed order");
               new Notification("balance", String.format("$%,.2f", HelloApplication.getUser().getBankAccount().getBalance()));
               HelloApplication.getOrder().setDate();
               int auth = user.getBankAccount().generateNewAuthNumber();
               user.getUserOrders().add(HelloApplication.getOrder());
               Files.saveUsers(HelloApplication.getSystemAccounts());
               System.out.println(auth);
               try{
                   Views.accountView(rootPane);
               }catch(Exception e){
                   new Notification("fail", "Failed to load account page");
                   System.out.println("Could not redirect to account view "+ e.getMessage());
               }
           }else{
               new Notification("error", "Failed: insufficient funds");
               new Notification("balance", String.format("$%,.2f", HelloApplication.getUser().getBankAccount().getBalance()));
               System.out.println("Error charging account: Insufficient funds!");
           }
        });
        HelloApplication.addDisplayModeListener(rootPane, displayMode);
    }

    /**
     * Display Method to build and reorganize view based on screen width
     */
    public void setupGrid(){
        ObservableList<Node> children = grid.getChildren();
        int counter = 0;
        for(Node node : children){
            GridPane.setRowIndex(node, (counter / columns));
            GridPane.setColumnIndex(node, (counter % columns));
            GridPane.setHalignment(node, HPos.CENTER);
            GridPane.setValignment(node, VPos.CENTER);
            counter++;
        }
    }


}
