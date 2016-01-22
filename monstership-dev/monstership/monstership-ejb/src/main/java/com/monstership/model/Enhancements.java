package com.monstership.model;


public class Enhancements {
    private int dodge;
    private int power;
    private int speed;

    public Enhancements(){
        this(0,0,0);
    }

    public Enhancements(int dodge, int power, int speed) {
        this.dodge = dodge;
        this.power = power;
        this.speed = speed;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
