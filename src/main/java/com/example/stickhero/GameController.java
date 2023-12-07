package com.example.stickhero;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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
    private Rectangle firstPlatform;
    @FXML
    private Rectangle secondPlatform;
    @FXML
    private Rectangle thirdPlatform;
    @FXML
    private Line stick;
    @FXML
    private AnchorPane gamePane;
    private Platform platform1 = new Platform(200, 45, 140, 455, firstPlatform);
    private Platform platform2 = new Platform(200, 77, 367, 455, secondPlatform);
    private Platform platform3 = new Platform(200, 98, 611, 455, thirdPlatform);
    private Hero myHero = new Hero(60, 45, 140, 400, heroImage);
    double stickLength;
    private long startTime;
    private static final double INCREASE_AMOUNT = 1.0;
    private static final long INCREASE_INTERVAL = 25; // milliseconds
    private int isMousePressed = 0;

    public void switchToStartScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameplayScreen.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();

        // Set the controller for FXMLLoader explicitly
        loader.setController(controller);

        // Rest of your code remains the same
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Set up mouse pressed and released event handlers
        controller.setMainWindow(stage); // Ensure the controller has a reference to the stage
        controller.initializeGame(); // Call a method to initialize game-related components

        // Start the loop in a separate thread
        new Thread(controller::increaseLengthLoop).start();
    }


    public void handleMousePressed(MouseEvent mouseEvent) {
        // Record the start time when the mouse is pressed
        startTime = System.currentTimeMillis();

        // Set isMousePressed to 1
        isMousePressed = 1;
    }

    public void handleMouseReleased(MouseEvent mouseEvent) {
        // Set isMousePressed to 0
        isMousePressed = 0;

        rotateStickAndMoveHero();
    }

    private void increaseLengthLoop() {
        while (true) {
            if (isMousePressed == 1) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;

                double newStickLength = stick.getEndY() - (INCREASE_AMOUNT * (elapsedTime / 200.0));

                stick.setEndY(newStickLength);
            }

            try {
                // Sleep for the specified interval
                Thread.sleep(INCREASE_INTERVAL);
            } catch (InterruptedException e) {
                // Handle interruption if needed
                e.printStackTrace();
            }
        }
    }

    private void rotateStickAndMoveHero() {
        // Set the pivot point to the bottom end of the line
        stick.setRotate(0); // Reset rotation
        stick.setStartY(((-1) * stick.getEndY()));
        stick.setTranslateY(firstPlatform.getTranslateY() + 1);

        // Create a RotateTransition for the line
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), stick);

        // Set the angle of rotation (90 degrees)
        rotateTransition.setByAngle(90);

        // Set event handler to trigger moveHeroImage after rotation is finished
        rotateTransition.setOnFinished(event -> moveHeroImage());

        // Play the rotation animation
        rotateTransition.play();
    }

    private void moveHeroImage() {
        // Create a TranslateTransition for heroImage
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), heroImage);

        double lowerBound = secondPlatform.getLayoutX() - (firstPlatform.getLayoutX() + firstPlatform.getWidth());
        System.out.println(lowerBound);
        double upperBound = lowerBound + secondPlatform.getWidth();
        System.out.println(upperBound);
        System.out.println(stickLength);
        double destinationX;

        if (((-1)*stickLength >= lowerBound) && ((-1)*stickLength <= upperBound)) {
            destinationX = secondPlatform.getLayoutX() - secondPlatform.getWidth() / 2;
        }
        else {
            destinationX = stick.getLayoutX() + (-1)*stickLength;
        }

        double destinationY = heroImage.getTranslateY();

        // Set the new position
        translateTransition.setToX(destinationX);
        translateTransition.setToY(destinationY);

        // Play the translation animation
        translateTransition.play();
    }
public void initializeGame() {
    gamePane.setOnMousePressed(this::handleMousePressed);
    gamePane.setOnMouseReleased(this::handleMouseReleased);
}

public void setMainWindow(Stage stage) {
        this.stage = stage;
    }
}