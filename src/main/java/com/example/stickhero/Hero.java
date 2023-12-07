package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Hero extends Solid{
    private Score score;
    private int cherries;
    @FXML
    private ImageView heroImage;

    public Hero(float length, float width, float xCoord, float yCoord, ImageView heroImage) {
        super(length, width, xCoord, yCoord);
        this.heroImage = heroImage;
    }

    public ImageView getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(ImageView heroImage) {
        this.heroImage = heroImage;
    }

    public void move(){}

    public void changePlatform(){}

    public void flip(){}

    public void revive(){}
}