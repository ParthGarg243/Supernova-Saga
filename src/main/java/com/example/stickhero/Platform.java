package com.example.stickhero;

public class Platform extends Solid{
    Platform nextPlatform;
    Platform previousPlatform;
    int points;
    RedArea redArea;
    public Platform(float length, float width, float xCoord, float yCoord) {
        super(length, width, xCoord, yCoord);
    }

    public void generateRedArea(){}
}
