package com.example.stickhero;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();
        Scene scene = new Scene(root, 406, 650);
        controller.setMainWindow(primaryStage);
        primaryStage.setTitle("stickhero");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}