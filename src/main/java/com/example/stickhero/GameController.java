package com.example.stickhero;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Random;

public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private double stickLength;
    private long startTime;
    private boolean isSpaceBarPressed = false;
    private boolean isHeroFlipped = false;
    private boolean forceStopThread = false;
    private static final double INCREASE_AMOUNT = 1.0;
    private static final long INCREASE_INTERVAL = 25; // milliseconds
    private int isMousePressed = 0;
    private int canFlip = 0;
    private int cherries;
    private double newWidth;
    private double newLayoutX;
    private Score score = new Score();
    private int spawnedCherry = 0;
    private int finalscore;
    Random random = new Random();
    @FXML
    private ImageView heroImage;
    @FXML
    private ImageView fliptry;
    @FXML
    private Rectangle firstPlatform;
    @FXML
    private Rectangle secondPlatform;
    @FXML
    private Rectangle thirdPlatform;
    @FXML
    private Line stick;
    @FXML
    private ImageView cherry;
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
    @FXML
    private ImageView newCherry;
    @FXML
    private ImageView newHeroImage;

    @FXML
    private Text scoreText = new Text(Integer.toString(score.getPoints()));
    @FXML
    private Text gameScore = new Text("Integer.toString(score.getPoints())");
    @FXML
    private Text bestScore = new Text(Integer.toString(score.getPoints()));
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

    private void switchToGameOverScreen() throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameOverScreen.fxml"));
        //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaxWidth(410);
        gameScore.setText(String.valueOf(finalscore));
        stage.show();
    }

    public void switchToGameScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameplayScreen.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();
        cherries = 0;
        forceStopThread = false;

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
        rotateTransition.setOnFinished(event -> {
            try {
                moveHeroImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Play the rotation animation
        rotateTransition.play();
    }

    private void moveHeroImage() throws IOException {
        // Create a TranslateTransition for heroImage
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), heroImage);
        int flag = 0;
        double lowerBound = secondPlatform.getLayoutX() - (firstPlatform.getLayoutX() + firstPlatform.getWidth());
        double upperBound = lowerBound + secondPlatform.getWidth();
        double destinationX;

        if (((-1) * stick.getEndY() >= lowerBound) && ((-1) * stick.getEndY() <= upperBound)) {
            destinationX = secondPlatform.getLayoutX();
            score.increasePoints();

            // Use Thread for smooth movement
            double startX = 0;
            long startTime = System.currentTimeMillis();

            Thread moveThread = new Thread(() -> {
                double newX = 0;
                while (newX < destinationX && !forceStopThread) {
                    double progress = (System.currentTimeMillis() - startTime) / 2000.0;
                    newX = startX + (destinationX - startX) * progress;

                    // Update the UI in the JavaFX application thread using Platform.runLater
                    double finalNewX = newX;
                    Platform.runLater(() -> {
                        if (isHeroFlipped && (finalNewX + (heroImage.getFitWidth()/2) > secondPlatform.getLayoutX())) {
                            forceStopThread = true;
                        }

                        heroImage.setLayoutX(finalNewX);

                        if (isHeroFlipped && spawnedCherry == 1 && finalNewX > cherry.getLayoutX()) {
                            removeCherry();
                        }
                    });

                    try {
                        // Sleep for a short duration for smooth animation
                        Thread.sleep(16); // 60 frames per second (1000ms / 60)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // After reaching the destination, trigger moveAll()
                if (!forceStopThread) {
                    Platform.runLater(this::moveAll);
                }
                else {
                    Platform.runLater(() -> {
                        gamePane.getChildren().remove(stick);
                        fallDown();
                        try {
                            endGame();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            });

            // Start the moveThread
            moveThread.start();
        }
        else {
            flag = 1;
            destinationX = heroImage.getLayoutX() + firstPlatform.getWidth() - stickLength;
            double destinationY = heroImage.getTranslateY();

            // Set the new position
            translateTransition.setToX(destinationX);
            translateTransition.setToY(destinationY);
            translateTransition.setOnFinished(event -> moveAll());

            translateTransition.setOnFinished(event -> {
                gamePane.getChildren().remove(stick);
                fallDown();
                try {
                    endGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            translateTransition.play();
        }
    }

    public void endGame() throws IOException {
        TranslateTransition p1 = new TranslateTransition(Duration.seconds(3), firstPlatform);
        TranslateTransition p2 = new TranslateTransition(Duration.seconds(3), secondPlatform);
        TranslateTransition p3 = new TranslateTransition(Duration.seconds(3), thirdPlatform);
        p1.setToY(460);
        p2.setToY(460);
        p3.setToY(460);

        p3.setOnFinished(event -> {
            try {
                finalgame();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        p1.play();
        p2.play();
        p3.play();
    }

    private void spawnCherry() {
        cherry.setLayoutX((secondPlatform.getLayoutX() + (firstPlatform.getLayoutX() + firstPlatform.getWidth()))/2);
        cherry.setLayoutY(465);
        spawnedCherry = 1;
    }

    private void removeCherry() {
        newCherry = new ImageView(cherry.getImage());
        newCherry.setLayoutX(420);
        newCherry.setLayoutY(115);
        newCherry.setFitWidth(cherry.getFitWidth());
        newCherry.setFitHeight(cherry.getFitHeight());
        newCherry.setPreserveRatio(cherry.isPreserveRatio());
        newCherry.setSmooth(cherry.isSmooth());
        newCherry.setCache(cherry.isCache());
        gamePane.getChildren().remove(cherry);
        gamePane.getChildren().add(newCherry);
        cherry = newCherry;
        cherries++;
        spawnedCherry = 0;
    }

    public void finalgame() throws IOException {
        System.out.println(score.getPoints());
        finalscore=score.getPoints();
        switchToGameOverScreen();
    }

    public void moveAll() {
        newWidth = PlatformClass.generateWidth();
        newLayoutX = PlatformClass.generateLayoutX();

        thirdPlatform.setWidth(newWidth);

        // Move hero
        TranslateTransition h = new TranslateTransition(Duration.seconds(2), heroImage);
        h.setToX(-(secondPlatform.getLayoutX()));
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

        h.play();
        s.play();
        p1.play();
        p2.play();
        p3.play();

        p3.setOnFinished(event -> {
            // Remove the old stick from the pane
            gamePane.getChildren().remove(stick);

            // Change platforms and update stick
            changePlatformsAndUpdateStick();
        });
    }


    public void fallDown() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), heroImage);

        rotateTransition.setByAngle(720);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), heroImage);

        translateTransition.setToY(655);

        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, translateTransition);

        parallelTransition.play();
    }

    public void flipHero(){
        if (isSpaceBarPressed && canFlip == 0) {
            if (isHeroFlipped == false) {
                double bottomY = heroImage.getLayoutY() + heroImage.getFitHeight();
                heroImage.setLayoutY(bottomY - 10);
                heroImage.setScaleY(heroImage.getScaleY() * -1);

                isHeroFlipped = !isHeroFlipped;

//                 Adjust the layoutY to keep the bottom part fixed
                fliptry.setScaleY(fliptry.getScaleY() * -1);
                fliptry.setLayoutY(-fliptry.getLayoutY() + fliptry.getFitHeight());
            }
            else {
                double bottomY = heroImage.getLayoutY() - heroImage.getFitHeight();
                heroImage.setLayoutY(bottomY + 10);
                heroImage.setScaleY(heroImage.getScaleY() * -1);

                isHeroFlipped = !isHeroFlipped;

                // Adjust the layoutY to keep the bottom part fixed
                fliptry.setScaleY(fliptry.getScaleY() * -1);
                fliptry.setLayoutY(-fliptry.getLayoutY() + fliptry.getFitHeight());
            }
        }
    }

    public void changePlatformsAndUpdateStick(){
        newHeroImage = new ImageView(heroImage.getImage());
        newHeroImage.setLayoutX(0);
        newHeroImage.setLayoutY(heroImage.getLayoutY());
        newHeroImage.setFitWidth(heroImage.getFitWidth());
        newHeroImage.setFitHeight(heroImage.getFitHeight());
        newHeroImage.setPreserveRatio(heroImage.isPreserveRatio());
        newHeroImage.setSmooth(heroImage.isSmooth());
        newHeroImage.setCache(heroImage.isCache());
        gamePane.getChildren().remove(heroImage);
        gamePane.getChildren().add(newHeroImage);
        heroImage = newHeroImage;

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

        stick = newStick;

        int randomNumber = random.nextInt(2); // Generates a random number between 0 (inclusive) and 4 (exclusive)

        if (randomNumber == 1) {
            spawnCherry();
        }

        stickLength = 10;
    }

    public void initializeGame() {
        gamePane.setOnMousePressed(this::handleMousePressed);
        gamePane.setOnMouseReleased(this::handleMouseReleased);

        gamePane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                isSpaceBarPressed = true;
                flipHero();
            }
        });

        // Add key released event handler for the space bar
        gamePane.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                isSpaceBarPressed = false;
            }
        });

        fliptry = new ImageView();

        gamePane.requestFocus();
    }

    public void setMainWindow(Stage stage) {
        this.stage = stage;
    }
}
