//package com.example.stickhero;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.shape.Line;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class GameController {
//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//    @FXML
//    private ImageView heroImage;
//    @FXML
//    private Rectangle currentPlatform;
//    @FXML
//    private Rectangle nextPlatform;
//    @FXML
//    private Line stick;
//    @FXML
//    private AnchorPane gamePane;
//
//    public void switchToStartScreen(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void switchToGameScreen(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
////    @FXML
////    public void keyPressed(KeyEvent event) {
////        if (event.getCode() == KeyCode.SPACE) {
////            spacebarPressed = true;
////            increaseLength();
////        }
////    }
////
////    @FXML
////    public void keyReleased(KeyEvent event) {
////        if (event.getCode() == KeyCode.SPACE) {
////            spacebarPressed = false;
////        }
////    }
//
//    public void increaseLength() {
//        // Increase the stick length by adjusting the endY coordinate
//        double newStickLength = stick.getEndY() - 25.0;  // Adjust the increment as needed
//        stick.setEndY(newStickLength);
//    }
//
//    public void setMainWindow(Stage stage) {
//        this.stage = stage;
//    }
//}

package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView heroImage;
    @FXML
    private Rectangle currentPlatform;
    @FXML
    private Rectangle nextPlatform;
    @FXML
    private Line stick;
    @FXML
    private AnchorPane gamePane;

    private long startTime;
    private Timeline decreaseTimeline;
    private static final double INCREASE_AMOUNT = 10.0;
    private static final Duration INCREASE_INTERVAL = Duration.millis(200);

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

        // Set up mouse pressed and released event handlers
        gamePane.setOnMousePressed(this::handleMousePressed);
        gamePane.setOnMouseReleased(this::handleMouseReleased);

        // Initialize the Timeline for decreasing stick length
        decreaseTimeline = new Timeline(
                new KeyFrame(INCREASE_INTERVAL, mouseEvent -> increaseLength())
        );
        decreaseTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        // Record the start time when the mouse is pressed
        startTime = System.currentTimeMillis();

        // Start the timeline when the mouse is pressed
        decreaseTimeline.play();
    }

    private void handleMouseReleased(MouseEvent mouseEvent) {
        // Stop the timeline when the mouse is released
        decreaseTimeline.stop();
    }

    private void increaseLength() {
        // Calculate elapsed time since the mouse was pressed
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        // Decrease the stick length based on elapsed time
        double newStickLength = stick.getEndY() - (INCREASE_AMOUNT * (elapsedTime / 200.0));

        // Update the stick length
        stick.setEndY(newStickLength);
    }

    public void setMainWindow(Stage stage) {
        this.stage = stage;
    }
}
