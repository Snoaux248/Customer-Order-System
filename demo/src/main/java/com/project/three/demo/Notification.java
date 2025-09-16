package com.project.three.demo;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Notification {
    /**
     * Constructor for Notifications
     * @param type : String
     * @param text : String
     */
    public Notification(String type, String text){
        if(type.equals("error")){
            pushNotification("notifError", text);
        }else if(type.equals("alert")){
            pushNotification("notifAlert", text);
        }else if(type.equals("balance")){
            pushNotification("notifBalance", text);
        }else if(type.equals("fail")){
            pushNotification("notifFail", text);
        }else if(type.equals("warning")){
            pushNotification("notifWarn", text);
        }else{
            new Notification("error", "Failed to generate error notification");
        }
    }

    /**
     * Display Method to build and display / remove notifications
     * @param className : String
     * @param text : String
     */
    private void pushNotification(String className, String text){
        ScrollPane scrollPane = (ScrollPane) HelloApplication.primaryStage.getScene().getRoot().lookup("#notificationView");
        VBox vBox = (VBox) scrollPane.getContent();

        AnchorPane newNotification = new AnchorPane();
        newNotification.getStyleClass().add("notif");
        newNotification.getStyleClass().add(className);
        Label label = new Label(text);
        label.getStyleClass().add("notif");
        newNotification.getChildren().add(label);
        vBox.getChildren().add(newNotification);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> vBox.getChildren().remove(newNotification)); // Remove the label after 3 sec
        pause.play();
    }
}
