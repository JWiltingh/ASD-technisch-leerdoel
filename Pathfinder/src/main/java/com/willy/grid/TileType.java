package com.willy.grid;

import javafx.scene.paint.Color;

public enum TileType {
    CONCRETE(0.4F, Color.GREY),
    WALL(100F, Color.BLACK),
    SAND(0.8F, Color.YELLOW),
    SNOW(1.2F, Color.WHITE),
    UNDEFINED(100F, Color.PURPLE),
    VISUAL(0.0F, Color.GREEN);

    private final float movementSpeed;
    private final Color color;

    TileType(float movementSpeed, Color color) {
        this.movementSpeed = movementSpeed;
        this.color = color;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Color getColor() {
        return color;
    }
}
