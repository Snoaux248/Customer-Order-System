package com.project.three.demo;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

public class OrderViewController{
    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private Button displayMode;
    @FXML private Button backButton;

    private int columns = 1;

    /**
     * Initialization Method for order-view page load
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
                Views.pastOrdersView(rootPane);
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
    public void setupGrid(){
        grid.getChildren().clear();
        int counter = 0;
        ArrayList<Item> ItemList;
        if(HelloApplication.getUser().getAdministratorStatus()) {
            ItemList =  HelloApplication.adminOrder.getShoppingCart();
        }else{
            ItemList = HelloApplication.getOrder().getShoppingCart();
        }
        for(Item item : ItemList){
            AnchorPane container = buildItemContainer(item);
            grid.add(container, (counter % columns), (counter / columns));
            GridPane.setHalignment(container, HPos.CENTER);
            GridPane.setValignment(container, VPos.CENTER);
            counter++;
        }
    }
    /**
     * Method for building new Order screen container
     * @param item : Item
     */
    public AnchorPane buildItemContainer(Item item){
        AnchorPane container = new AnchorPane();

        Label label = new Label(item.toString());
        label.setId("name");
        AnchorPane.setTopAnchor(label,0.0);
        AnchorPane.setLeftAnchor(label,0.0);
        Label quantity = new Label(Integer.toString(item.getQuantity()));
        quantity.setId("quantity");

        AnchorPane.setTopAnchor(quantity,-20.0);
        AnchorPane.setRightAnchor(quantity,-20.0);
        quantity.getStyleClass().add("notification");

        container.getChildren().add(label);
        container.getChildren().add(quantity);
        container.getStyleClass().add("orderItem");
        return container;
    }
}