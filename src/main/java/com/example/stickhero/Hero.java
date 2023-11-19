package com.example.stickhero;

public class Hero extends Solid{
    private Score score;
    private Platform currentPlatform;
    private Platform nextPlatform;
    private Stick stick;
    private int cherries;

    public Hero(float length, float width, float xCoord, float yCoord) {
        super(length, width, xCoord, yCoord);
    }

    public void move(){}

    public void changePlatform(){}

    public void flip(){}

    public void revive(){}
}