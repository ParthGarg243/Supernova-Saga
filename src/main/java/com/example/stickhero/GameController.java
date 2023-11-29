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
import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private MediaPlayer mediaPlayer;
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
    @FXML
    private AnchorPane movableobjects;
    @FXML
    private AnchorPane pause;

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
        stick.setTranslateY(currentPlatform.getTranslateY() + 1);

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

        // Set the destination X coordinate (adjust as needed)
        double destinationX = nextPlatform.getLayoutX() - nextPlatform.getWidth() / 2;
//        double destinationX = heroImage.getLayoutX() - stick.getEndY() - heroImage.getFitWidth()/2;


        // Set the destination Y coordinate (same as the current position)
        double destinationY = heroImage.getTranslateY();

        // Set the new position
        translateTransition.setToX(destinationX);
        translateTransition.setToY(destinationY);
        translateTransition.setOnFinished(event -> moveAllObjects());
        // Play the translation animation
        translateTransition.play();
    }

//    public void initializeGame() throws URISyntaxException{
//        gamePane.setOnMousePressed(this::handleMousePressed);
//        gamePane.setOnMouseReleased(this::handleMouseReleased);
//
//        Media sound = new Media("src/main/resources/com/example/stickhero/musics/space_line-27593.mp3");
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//
//        //      Media media = new Media(getClass().getResource("/music/background.mp3").toURI().toString());
////            mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.play();
//        //gamePane.setOnMouseReleased(this::playsong);
//    }
public void initializeGame() {
    gamePane.setOnMousePressed(this::handleMousePressed);
    gamePane.setOnMouseReleased(this::handleMouseReleased);

    try {
        Media sound = new Media(getClass().getResource("/music/background.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        // Handle exception for music URI
        Media music = new Media("src/main/resources/com/example/stickhero/musics/space_line-27593.mp3");
        mediaPlayer = new MediaPlayer(music);
    } catch (URISyntaxException e) {
        // Handle the exception appropriately
        e.printStackTrace();
        // Provide a fallback or notify the user
    }
}

    //gamePane.setOnMouseReleased(this::playsong);



    public void moveAllObjects() {


        // Create a TranslateTransition
        TranslateTransition moveallobjects = new TranslateTransition(Duration.seconds(2), movableobjects);

        // NEED TO CHANGE X AXIS BY CALCULATING THE LENGTH OF STICK
        moveallobjects.setByX(-100);
        moveallobjects.setByY(0);

    // Play the transition
        moveallobjects.play();
    }


    //FUNCTION TO MOVE GLASSMORPHISM SCREEN WHEN PAUSED
//    public void movepause() {
//
//
//        TranslateTransition pausescreen = new TranslateTransition(Duration.seconds(2), pause);
//
//        // Set translation parameters
//        pausescreen.setByX(0);
//        pausescreen.setByY(-100);
//
//
//        pausescreen.play();
//    }
//    public void playsong() throws URISyntaxException {
//        Media sound = new Media("src/main/resources/com/example/stickhero/musics/space_line-27593.mp3");
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//
//        //      Media media = new Media(getClass().getResource("/music/background.mp3").toURI().toString());
////            mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.play();
//    }




    public void setMainWindow(Stage stage) {
        this.stage = stage;
    }
}