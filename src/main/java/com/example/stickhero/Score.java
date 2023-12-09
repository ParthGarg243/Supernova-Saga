package com.example.stickhero;

public class Score{
    private int points;
    private int time;

    public Score() {
        this.points = 0;
        this.time = 0;
    }

    public int getPoints() {
        return points;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void increasePoints() {
        this.points+=1;
    }

    public void increaseTime() {
    }
}