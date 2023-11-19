package com.example.stickhero;

public class Stick extends Solid{
    public Stick(float length, float width, float xCoord, float yCoord) {
        super(length, width, xCoord, yCoord);
    }

    public void rotate(){}

    public boolean isHeroBelow(){return true;}

    public void increaseLength(){};
}
