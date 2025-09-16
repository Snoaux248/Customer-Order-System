package com.project.three.demo;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class Views {
    /**
     * View Method for to load login-view page
     * @param rootPane : AnchorPane
     */
    public static void loginView(AnchorPane rootPane) throws IOException {
        rootPane = updatePageHelper(rootPane, "login-view.fxml", "Login");
    }
    /**
     * View Method for to load account-view page
     * @param rootPane : AnchorPane
     */
    public static void accountView(AnchorPane rootPane) throws IOException {
        rootPane = updatePageHelper(rootPane, "account-view.fxml", "Account");
    }
    /**
     * View Method for to load register-view page
     * @param rootPane : AnchorPane
     */
    public static void registerView(AnchorPane rootPane) throws IOException {
        rootPane = updatePageHelper(rootPane, "register-view.fxml", "Register");
    }
    /**
     * View Method for to load new-order-view page (from account-view page)
     * @param rootPane : AnchorPane
     */
    public static void newOrderView(AnchorPane rootPane) throws IOException {
        rootPane = updatePageHelper(rootPane, "new-order-view.fxml", "New Order");
    }
    /**
     * View Method for to load past-orders-view page
     * @param rootPane : AnchorPane
     */
    public static void pastOrdersView(AnchorPane rootPane) throws IOException {
        rootPane = updatePageHelper(rootPane, "past-orders-view.fxml", "Previous Orders");
    }
    /**
     * View Method for to load order-view page
     * @param rootPane : AnchorPane
     */
    public static void pastOrderView(AnchorPane rootPane) throws IOException {
        if(HelloApplication.getUser().getAdministratorStatus()){
            rootPane = updatePageHelper(rootPane, "order-view.fxml", "Order " + HelloApplication.adminOrder.getOrderID());
        }else {
            rootPane = updatePageHelper(rootPane, "order-view.fxml", "Order " + HelloApplication.getOrder().getOrderID());
        }
    }
    /**
     * View Method for to load security-view page
     * @param rootPane : AnchorPane
     */
    public static void securityView(AnchorPane rootPane) throws IOException {
        rootPane = updatePageHelper(rootPane, "security-view.fxml", "Security Check");
    }
    /**
     * View Method for to load order-preview-view page
     * @param rootPane : AnchorPane
     */
    public static void orderPreviewView(AnchorPane rootPane) throws IOException {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        ArrayList<Node> oldNodes = getOldItems(rootPane);
        rootPane = updatePageHelper(rootPane, "order-preview-view.fxml", "Preview Order");
        setNewItems(rootPane, oldNodes);
    }
    /**
     * View Method for to load new-order-view page (from order-preview-view page)
     * @param rootPane : AnchorPane
     */
    public static void backToOrderView(AnchorPane rootPane) throws IOException {
        HelloApplication.backPropagateOrder = true;
        ArrayList<Node> oldNodes = getOldItems(rootPane);
        rootPane = updatePageHelper(rootPane, "new-order-view.fxml", "New Order");
        mergeNewItems(rootPane, oldNodes);
    }

    /**
     * Helper Method assist in transferring objects between views
     * @param rootPane : AnchorPane
     * @param FXML_FILE : String
     * @param title : String
     */
    public static AnchorPane updatePageHelper(AnchorPane rootPane, String FXML_FILE, String title) throws IOException {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        String displayMode = getOldDisplayButton(rootPane);
        VBox vBox = getNotifications(rootPane);
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(Views.class.getResource("/com/project/three/demo/"+FXML_FILE)));
        stage.getScene().setRoot(pane);
        setNewDisplayButton(pane, displayMode);
        setNotifications(pane, vBox);
        stage.setTitle(title);
        return pane;
    }

    /**
     * Helper Method to retrieve state of dark mode button before view change
     * @param rootPane : AnchorPane
     */
    private static String getOldDisplayButton(AnchorPane rootPane) {
        Button displayModeButton = (Button) rootPane.getScene().lookup("#displayMode");
        return displayModeButton.getText();
    }
    /**
     * Helper Method to set state of dark mode button after view change
     * @param rootPane : AnchorPane
     * @param displayMode : String
     */
    private static void setNewDisplayButton(AnchorPane rootPane, String displayMode) {
        Button displayModeButton = (Button) rootPane.getScene().lookup("#displayMode");
        displayModeButton.setText(displayMode);
    }

    /**
     * Helper Method to retrieve selected catalogue items before view change
     * @param rootPane : AnchorPane
     * @return : ArayList<Node>
     */
    private static ArrayList<Node> getOldItems(AnchorPane rootPane) {
        ArrayList<Item> catalogue = new ArrayList<>();
        Files.loadCatalogue(catalogue);
        Order order = new Order();

        ScrollPane scroll = (ScrollPane) rootPane.getChildren().get(0);
        GridPane grid = (GridPane) scroll.getContent();
        Set<Node> oldNodes = grid.lookupAll(".catalogueItem");
        ArrayList<Node> newNodes = new ArrayList<>();

        int counter = 0;
        for (Node node : oldNodes) {
            if (node.lookup(".notification").getOpacity() == 1.0) {
                newNodes.add(node);

                Label temp = (Label) node.lookup(".notification");
                int quantity = Integer.parseInt(temp.getText().trim());
                Item item = new Item(catalogue.get(counter), quantity);
                order.addItem(item);
            }
            counter++;
        }
        if(((CheckBox)rootPane.lookup("#delivery")).isSelected()){
            order.setDeliveryType(true);
        }
        HelloApplication.setOrder(order);
        return newNodes;
    }
    /**
     * Helper Method to set selected catalogue items after view change
     * @param rootPane : AnchorPane
     * @param items : ArrayList<Node>
     */
    private static void setNewItems(AnchorPane rootPane, ArrayList<Node> items) {
        ScrollPane scroll = (ScrollPane) rootPane.getChildren().get(0);
        GridPane grid = (GridPane) scroll.getContent();
        //350: adjustment for total pane, 300 width of item pane
        int columns = (int) (HelloApplication.primaryStage.getWidth() - 360) / 300;
        columns = columns == 0 ? 1 : columns;
        int counter = 0;
        for (Node node : items) {
            grid.add(node, (counter % columns), (counter / columns));
            node.lookup("#addButton").setOpacity(0.0);
            node.lookup("#removeButton").setOpacity(0.0);
            node.lookup("#addButton").setDisable(true);
            node.lookup("#removeButton").setDisable(true);
            GridPane.setHalignment(node, HPos.CENTER);
            GridPane.setValignment(node, VPos.CENTER);
            counter++;
        }
    }
    /**
     * Helper Method merge order selection with catalogue items after view change
     * @param rootPane : AnchorPane
     * @param items : ArrayList<Node>
     */
    private static void mergeNewItems(AnchorPane rootPane, ArrayList<Node> items) {
        ScrollPane scroll = (ScrollPane) rootPane.getChildren().get(0);
        GridPane grid = (GridPane) scroll.getContent();

        ArrayList<Item> catalogueItems = new ArrayList<>();
        Files.loadCatalogue(catalogueItems);
        int counter = 0;
        //300: adjustment for total pane, 300 width of item pane
        int columns = (int) HelloApplication.primaryStage.getWidth() / 300;
        columns = columns == 0 ? 1 : columns;
        for (Item item : catalogueItems) {
            boolean found = false;
            for (Node node : items) {
                Label label = (Label) node.lookup("#name");
                if (item.toString().equals(label.getText())) {
                    grid.add(node, (counter % columns), (counter / columns));
                    found = true;
                    node.lookup("#addButton").setOpacity(1.0);
                    node.lookup("#addButton").setDisable(false);
                    node.lookup("#removeButton").setOpacity(1.0);
                    node.lookup("#removeButton").setDisable(false);
                }
            }
            if (!found) {
                AnchorPane container = NewOrderController.buildItemContainer(item);
                grid.add(container, (counter % columns), (counter / columns));
                GridPane.setHalignment(container, HPos.CENTER);
                GridPane.setValignment(container, VPos.CENTER);
                NewOrderController.addButtonAddListener((Button) container.lookup("#addButton"));
                NewOrderController.removeButtonAddListener((Button) container.lookup("#removeButton"));
            }
            counter++;
        }
    }
    /**
     * Helper Method to retrieve notifications before view change
     * @param rootPane : AnchorPane
     * @param vBox : VBox
     */
    private static void setNotifications(AnchorPane rootPane, final VBox vBox){
        ScrollPane scroll = (ScrollPane) rootPane.lookup("#notificationView");
        scroll.setContent(vBox);
    }
    /**
     * Helper Method to set notifications after viewLoad
     * @param rootPane : AnchorPane
     * @return : VBox
     */
    private static VBox getNotifications(AnchorPane rootPane) {
        ScrollPane scroll = (ScrollPane) rootPane.lookup("#notificationView");
        return (VBox) scroll.getContent();
    }
}
