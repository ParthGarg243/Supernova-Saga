package com.example.stickhero;

public class Solid {
    private float xCoord;
    private float yCoord;
    private float length;
    private float width;
    private float yVelocity;
    private float xVelocity;

    public Solid(float length, float width, float xCoord, float yCoord) {
        this.length = length;
        this.width = width;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public float getxCoord() {
        return xCoord;
    }

    public float getyCoord() {
        return yCoord;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setxCoord(float xCoord) {
        this.xCoord = xCoord;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyCoord(float yCoord) {
        this.yCoord = yCoord;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }
}
