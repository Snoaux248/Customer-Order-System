package com.project.three.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

public class NewOrderController {

    @FXML private AnchorPane rootPane;
    @FXML private GridPane grid;

    @FXML private Button displayMode;
    @FXML private Button backButton;
    @FXML private Button previewOrder;
    @FXML private CheckBox delivery;

    private int columns = 1;
    /**
     * Initialization Method for new-order-view page load
     */
    public void initialize(){
        columns = (int) HelloApplication.primaryStage.getWidth() / 300;
        columns = columns == 0 ? 1: columns;
        if(!HelloApplication.backPropagateOrder) {
            setupGrid();
        }else{
            HelloApplication.backPropagateOrder = false;
        }

        HelloApplication.primaryStage.widthProperty().addListener(event -> {
            int columnsTemp = (int)HelloApplication.primaryStage.getWidth() / 300;
            if(columnsTemp != columns){
                columns = columnsTemp == 0 ? 1: columnsTemp;
                reOrganizeGrid();
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
        previewOrder.setOnAction(event ->{
            try{
                Views.orderPreviewView(rootPane);
            }catch(IOException e){
                new Notification("fail", "Failed to load order preview page");
                System.out.println("Failed to load account page: "+ e.getMessage());
            }
        });
        HelloApplication.addDisplayModeListener(rootPane, displayMode);
    }

    /**
     * Display Method to build and reorganize view based on screen width(when loaded from account-view page)
     */
    public void setupGrid(){
        grid.getChildren().clear();
        int counter = 0;
        ArrayList<Item> catalogue = new ArrayList<>();
        Files.loadCatalogue(catalogue);
        for(Item item : catalogue){
            AnchorPane container = buildItemContainer(item);

            grid.add(container, (counter % columns), (counter / columns));
            GridPane.setHalignment(container, HPos.CENTER);
            GridPane.setValignment(container, VPos.CENTER);
            addButtonAddListener((Button)container.lookup("#addButton"));
            removeButtonAddListener((Button)container.lookup("#removeButton"));
            counter++;
        }
    }
    /**
     * Method to reorganize screen based upon width
     */
    public void reOrganizeGrid(){
        ObservableList<Node> children = grid.getChildren();
        //grid.getChildren().clear();
        int counter = 0;
        for(Node node : children){
            GridPane.setRowIndex(node, (counter / columns));
            GridPane.setColumnIndex(node, (counter % columns));
            GridPane.setHalignment(node, HPos.CENTER);
            GridPane.setValignment(node, VPos.CENTER);
            counter++;
        }
    }

    /**
     * Method for adding event listener to increase item quantity
     * @param addButton : Button
     */
    public static void addButtonAddListener(Button addButton){
        addButton.setOnMouseClicked(event -> {
            Node sourceNode = (Node) event.getSource();
            Label child = (Label)(sourceNode.getParent()).lookup("#quantity");
            int count = Integer.parseInt(child.getText());
            child.setText(Integer.toString(count+1));
            if(count >= 0){
                child.setOpacity(1.0);
                ((sourceNode.getParent()).lookup("#removeButton")).setOpacity(1.0);
            }
        });
    }
    /**
     * Method for adding event listener to decrease item quantity
     * @param removeButton : Button
     */
    public static void removeButtonAddListener(Button removeButton){
        removeButton.setOnMouseClicked(event -> {
            Node sourceNode = (Node) event.getSource();
            Label child = (Label)(sourceNode.getParent()).lookup("#quantity");
            int count = Integer.parseInt(child.getText());
            child.setText(Integer.toString(count == 0 ? 0 : count-1));
            if(count == 1){
                child.setOpacity(0.0);
                ((sourceNode.getParent()).lookup("#removeButton")).setOpacity(0.0);
            }
        });
    }
    /**
     * Method for building new catalogue item screen container
     * @param item : Item
     */
    public static AnchorPane buildItemContainer(Item item){
        AnchorPane container = new AnchorPane();

        Label label = new Label(item.toString());
        label.setId("name");
        AnchorPane.setTopAnchor(label,0.0);
        AnchorPane.setLeftAnchor(label,0.0);
        Label quantity = new Label("0");
        quantity.setId("quantity");
        quantity.setOpacity(0.0);

        AnchorPane.setTopAnchor(quantity,-20.0);
        AnchorPane.setRightAnchor(quantity,-20.0);
        quantity.getStyleClass().add("notification");

        Button addButton = new Button("+");
        AnchorPane.setBottomAnchor(addButton,0.0);
        AnchorPane.setRightAnchor(addButton,0.0);
        addButton.setId("addButton");

        Button removeButton = new Button("-");
        AnchorPane.setBottomAnchor(removeButton,0.0);
        AnchorPane.setRightAnchor(removeButton,55.0);
        removeButton.setId("removeButton");
        removeButton.setOpacity(0.0);

        container.getChildren().add(label);
        container.getChildren().add(quantity);
        container.getChildren().add(addButton);
        container.getChildren().add(removeButton);
        container.getStyleClass().add("catalogueItem");
        return container;
    }


}
