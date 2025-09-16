package com.project.three.demo;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

public class PastOrdersViewController {
    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private Button displayMode;
    @FXML private Button backButton;

    private int columns = 1;
    /**
     * Initialization Method for past-orders-view page load
     */
    public void initialize(){
        columns = (int)HelloApplication.primaryStage.getWidth() / 300;
        columns = columns == 0 ? 1: columns;
        setupGrid();
        HelloApplication.primaryStage.widthProperty().addListener(event -> {
            int columnsTemp = (int)HelloApplication.primaryStage.getWidth() / 300;
            if(columnsTemp != columns){
                columns = columnsTemp == 0 ? 1: columnsTemp;
                setupGrid();
            }
        });

        backButton.setOnAction(event ->{
            try{
                Views.accountView(rootPane);
            }catch(IOException e){
                new Notification("fail", "Failed to load account page");
                System.out.println("Failed to load account page: "+ e.getMessage());
            }
        });

        HelloApplication.addDisplayModeListener(rootPane, displayMode);

    }

    /**
     * Display Method to build and reorganize view based on screen width
     */
    private void setupGrid(){
        grid.getChildren().clear();
        int counter = 0;
        ArrayList<Order> orderList;
        if(HelloApplication.getUser().getAdministratorStatus()) {
            orderList =  HelloApplication.orders;
        }else{
            orderList = HelloApplication.getUser().getUserOrders();
        }
        for(Order order: orderList){
            Button button = new Button();
            button.setText("Order ID: "+ order.getOrderID() +
                    "\nDate: "+ order.getDate() +
                    "\nDelivery method: "+ (order.getDeliveryType() ? "Delivery": "Pickup") +
                    "\nPrice: "+ String.format("$%,.2f", order.getPrice()) +
                    "\nDiscount: "+ String.format("$%,.2f", order.getDiscount()) +
                    "\nTotal: "+ String.format("$%,.2f", order.calculateTotal()));
            button.getStyleClass().add("submenu");
            grid.add(button, (counter % columns), (counter / columns));
            GridPane.setHalignment(button, HPos.CENTER);
            GridPane.setValignment(button, VPos.CENTER);
            button.setOnAction(event ->{
                if(HelloApplication.getUser().getAdministratorStatus()) {
                    HelloApplication.adminOrder = order;
                }else{
                    HelloApplication.setOrder(order);
                }
                try{
                    Views.pastOrderView(rootPane);
                }catch(IOException e){
                    System.out.println("Failed to load past order page: "+ e.getMessage());
                }
            });
            counter++;
        }
    }
}
