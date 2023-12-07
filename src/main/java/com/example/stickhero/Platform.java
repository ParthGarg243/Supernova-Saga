package com.example.stickhero;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class Platform extends Solid{
    @FXML
    private Rectangle thisPlatform;
    int points;
    RedArea redArea;

    public Platform(float length, float width, float xCoord, float yCoord, Rectangle rectangle) {
        super(length, width, xCoord, yCoord);
        this.thisPlatform = rectangle;
    }

    public Rectangle getThisPlatform() {
        return thisPlatform;
    }

    public void generateRedArea(){}
}
