package com.example.stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToStartScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("startscreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setWidth(600);
        stage.setHeight(518);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setWidth(600);
        stage.setHeight(518);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameOverScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("gameoverscreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setWidth(600);
        stage.setHeight(518);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPauseScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("pausescreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setWidth(600);
        stage.setHeight(518);
        stage.setScene(scene);
        stage.show();
    }
}