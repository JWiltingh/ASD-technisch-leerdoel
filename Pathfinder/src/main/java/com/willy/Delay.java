package com.willy;

public class Delay {
    private double delayTime;
    private double accumulatedTime;
    private long lastTime;

    public Delay(double delayTime) {
        this.delayTime = delayTime;
        this.accumulatedTime = 0;
        this.lastTime = System.nanoTime();
    }

    public boolean isReady() {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        accumulatedTime += deltaTime;

        if (accumulatedTime >= delayTime) {
            accumulatedTime -= delayTime;
            return true;
        }
        return false;
    }

    public void reset() {
        accumulatedTime = 0;
        lastTime = System.nanoTime();
    }

    public void adjustMovementDelay(double delayTime) {
        this.delayTime = delayTime;
    }

    public double getDelayTime() {
        return delayTime;
    }
}

