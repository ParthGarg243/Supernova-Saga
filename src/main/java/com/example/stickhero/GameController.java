package com.example.stickhero;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Hero myHero = new Hero(60, 45, 25, 400);
    private Platform currentPlatform = new Platform();

    public void switchToStartScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ImageView mainscreen;
    public void setMainWindow(Stage stage) {
        this.stage = stage;
    }
    private void initialize(){
        mainscreen.setImage(new Image(getClass().getResourceAsStream("src/images/startscreen.png")));
    }
}