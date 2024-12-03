package com.willy.grid;

import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private final TileType type;
    private final int xPosition;
    private final int yPosition;

    public Tile(TileType type, int size, int xPosition, int yPosition) {
        super(size, size, type.getColor());
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.setStroke(type.getColor());
        this.setFill(type.getColor());
    }

    public float getMovementSpeed() {
        return type.getMovementSpeed();
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public TileType getType() {
        return type;
    }
}
