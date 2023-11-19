package com.example.stickhero;

public class Cherry extends Solid{
    private int points;
    public Cherry(float length, float width, float xCoord, float yCoord) {
        super(length, width, xCoord, yCoord);
        this.points = 3;
    }
}
