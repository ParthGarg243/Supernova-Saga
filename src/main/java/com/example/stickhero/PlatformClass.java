package com.example.stickhero;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class PlatformClass extends Rectangle{
    @FXML
    private Rectangle thisPlatform;
    int points;
    RedArea redArea;

    public PlatformClass(float length, float width, float xCoord, float yCoord, Rectangle rectangle) {
        super(length, width, xCoord, yCoord);
        this.thisPlatform = rectangle;
    }

    public static double generateWidth() {
        Random random = new Random();

        return random.nextInt(21) + 45;
    }

    public static double generateLayoutX() {
        Random random = new Random();

        return random.nextInt(236) + 90;
    }

    public Rectangle getThisPlatform() {
        return thisPlatform;
    }

    public void generateRedArea(){}
}
