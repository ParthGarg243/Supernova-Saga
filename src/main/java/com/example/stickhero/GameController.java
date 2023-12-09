package com.example.stickhero;

import javafx.animation.*;
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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    //private int score = 0;
    private double stickLength;
    private long startTime;
    private static final double INCREASE_AMOUNT = 1.0;
    private static final long INCREASE_INTERVAL = 25; // milliseconds
    private int isMousePressed = 0;
    private int canFlip = 0;
    private double newWidth;
    private double newLayoutX;
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
    @FXML
    private Rectangle newFirst;
    @FXML
    private Rectangle newSecond;
    @FXML
    private Rectangle newThird;
    @FXML
    private Line newStick;
    private Score score=new Score();
    @FXML
    private Text scoreText = new Text(Integer.toString(score.getPoints()));
    @FXML
    private ImageView cherryimage;

//    private Platform platform1 = new Platform(200, 45, 140, 455, firstPlatform);
//    private Platform platform2 = new Platform(200, 77, 367, 455, secondPlatform);
//    private Platform platform3 = new Platform(200, 98, 611, 455, thirdPlatform);
//    private final Hero myHero = new Hero(60, 45, 140, 400, heroImage);

    public void switchToStartScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameOverScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameOverScreen.fxml"));
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
        stage.setMaxWidth(410);
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
        stickLength = 10;
        while (true) {
            if (isMousePressed == 1) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;

                double newStickLength = stick.getEndY() - (INCREASE_AMOUNT * (elapsedTime / 200.0));
                stickLength -= INCREASE_AMOUNT * (elapsedTime / 200.0);

                stick.setEndY(newStickLength);
            }

            try {
                // Sleep for the specified interval
                Thread.sleep(INCREASE_INTERVAL);
            }
            catch (InterruptedException e) {
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
        int flag = 0;
        double lowerBound = secondPlatform.getLayoutX() - (firstPlatform.getLayoutX() + firstPlatform.getWidth());
        double upperBound = lowerBound + secondPlatform.getWidth();
        double destinationX;

        if (((-1) * stick.getEndY() >= lowerBound) && ((-1) * stick.getEndY() <= upperBound)) {
            destinationX = secondPlatform.getLayoutX();
            score.increasePoints();

        }
        else {
            flag = 1;
            destinationX = heroImage.getLayoutX() + firstPlatform.getWidth() - stickLength;
        }

        double destinationY = heroImage.getTranslateY();

        // Set the new position
        translateTransition.setToX(destinationX);
        translateTransition.setToY(destinationY);
        translateTransition.setOnFinished(event -> moveAll());

        if (flag == 1) {
            translateTransition.setOnFinished(event -> {
                gamePane.getChildren().remove(stick);
                fallDown();
                endGame();
            });
        }
        else {
            scoreText.setText(Integer.toString(score.getPoints()));
        }

        canFlip = 1;
        translateTransition.play();
        canFlip = 0;
    }

    public void endGame() {
        TranslateTransition p1 = new TranslateTransition(Duration.seconds(3), firstPlatform);
        TranslateTransition p2 = new TranslateTransition(Duration.seconds(3), secondPlatform);
        TranslateTransition p3 = new TranslateTransition(Duration.seconds(3), thirdPlatform);
        p1.setToY(460);
        p2.setToY(460);
        p3.setToY(460);
        p1.play();
        p2.play();
        p3.play();

    }

    public void moveAll() {
        newWidth = Platform.generateWidth();
        newLayoutX = Platform.generateLayoutX();

        thirdPlatform.setWidth(newWidth);

        // Move hero
        TranslateTransition h = new TranslateTransition(Duration.seconds(2), heroImage);
        h.setToX(0);
        h.setToY(0);

        // Move stick
        TranslateTransition s = new TranslateTransition(Duration.seconds(2), stick);
        s.setToX(-410);
        s.setToY(0);

        // Move platforms
        TranslateTransition p1 = new TranslateTransition(Duration.seconds(2), firstPlatform);
        p1.setToX(-(secondPlatform.getLayoutX()));
        p1.setToY(0);

        TranslateTransition p2 = new TranslateTransition(Duration.seconds(2), secondPlatform);
        p2.setToX(-(secondPlatform.getLayoutX()));
        p2.setToY(0);

        TranslateTransition p3 = new TranslateTransition(Duration.seconds(2), thirdPlatform);
        p3.setToX(-(410 - newLayoutX));
        p3.setToY(0);

        //long bound= (long) ((410 - newLayoutX)-(long) secondPlatform.getLayoutX());
        //Random cherryPosition=new Random(bound);
        TranslateTransition c = new TranslateTransition(Duration.seconds(2), cherryimage);

        //c.setToX(c.ge+(-(410 - newLayoutX))+40);
        //c.setToY(0);
        //cherryimage.setLayoutX(-(410 - newLayoutX));
        //cherryimage.setLayoutY(403);
        h.play();
        s.play();
        p1.play();
        p2.play();
        p3.play();
        c.play();

        p3.setOnFinished(event -> {
            changePlatformsAndUpdateStick();
        });
    }

    public void fallDown() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), heroImage);

        rotateTransition.setByAngle(720);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), heroImage);

        translateTransition.setToY(655);

        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, translateTransition);
        parallelTransition.setOnFinished(event -> {
            try {
                switchToGameOverScreen(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        parallelTransition.play();
//        Duration delay=Duration.seconds(2);
//
//        Timeline timeline=new Timeline((new KeyFrame(delay,event -> {
//            try {
//                root=FXMLLoader.load(getClass().getResource("GameOverScreen.fxml"));
//                stage=(Stage)((Node)event.getSource()).getScene().getWindow();
//                scene=new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }catch (IOException i){
//                System.out.println("IO excdeption");
//            }
  //      })));

    }

    public void flipHero(){
        heroImage.setScaleY(-1);
        heroImage.setLayoutY(heroImage.getLayoutY() + heroImage.getFitHeight() );

    }

    public void changePlatformsAndUpdateStick(){
        firstPlatform.setLayoutX(-100);
        firstPlatform.setLayoutY(455);
        secondPlatform.setLayoutX(0);
        secondPlatform.setLayoutY(455);
        thirdPlatform.setLayoutX(thirdPlatform.getLayoutX() - (410 - newLayoutX));
        thirdPlatform.setLayoutY(455);

        newFirst = new Rectangle(secondPlatform.getWidth(), secondPlatform.getHeight());
        newFirst.setLayoutX(0);
        newFirst.setLayoutY(secondPlatform.getLayoutY());

        newSecond = new Rectangle(thirdPlatform.getWidth(), thirdPlatform.getHeight());
        newSecond.setLayoutX(thirdPlatform.getLayoutX());
        newSecond.setLayoutY(thirdPlatform.getLayoutY());

        newThird = new Rectangle(newWidth, thirdPlatform.getHeight());
        newThird.setLayoutX(410);
        newThird.setLayoutY(thirdPlatform.getLayoutY());

        gamePane.getChildren().removeAll(firstPlatform, secondPlatform, thirdPlatform);
        gamePane.getChildren().addAll(newFirst, newSecond, newThird);

        firstPlatform = newFirst;
        secondPlatform = newSecond;
        thirdPlatform = newThird;

        newStick = new Line();
        newStick.setStartX(0);
        newStick.setStartY(0);
        newStick.setEndX(0);
        newStick.setEndY(-10);
        newStick.setStrokeWidth(3.0);
        newStick.setStrokeLineCap(StrokeLineCap.ROUND);
        newStick.setLayoutX(firstPlatform.getLayoutX() + firstPlatform.getWidth());
        newStick.setLayoutY(455);

        gamePane.getChildren().removeAll(newStick);
        gamePane.getChildren().addAll(newStick);

        stickLength = 10;
        stick = newStick;
    }

    public void initializeGame() {
        gamePane.setOnMousePressed(this::handleMousePressed);
        gamePane.setOnMouseReleased(this::handleMouseReleased);
    }

    public void setMainWindow(Stage stage) {
        this.stage = stage;
    }
}
