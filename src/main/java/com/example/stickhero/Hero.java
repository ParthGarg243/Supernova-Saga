package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Hero extends ImageView{
    private Score score;
    private int cherries;
    private static Hero hero = null;
    @FXML
    private ImageView heroImage;
    public static ImageView getInstance(ImageView hero)
    {
        if (hero == null) {
            hero = new Hero(hero);
        }
        return hero;
    }
    private Hero(ImageView hero) {
        this.heroImage = hero;
    }

    public void move(){}

    public void changePlatform(){}

    public void flip(){}

    public void revive(){}
}