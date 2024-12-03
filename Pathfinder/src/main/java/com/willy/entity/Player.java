package com.willy.entity;

import com.willy.Delay;
import com.willy.grid.Grid;
import com.willy.grid.TileType;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class Player extends Rectangle implements Entity {
    private IntegerProperty xPosition = new SimpleIntegerProperty(0);
    private IntegerProperty  yPosition = new SimpleIntegerProperty(0);
    private final Map<KeyCode, Runnable> movementMap;
    private Delay movementDelay = new Delay(0.4);
    private Grid grid;

    public Player(int size, Grid grid) {
        super(size, size);
        setStroke(Color.BLUE);
        setFill(Color.DARKBLUE);
        this.grid = grid;

        movementMap = Map.of(
                KeyCode.UP, () -> move(0, -1),
                KeyCode.DOWN, () -> move(0, 1),
                KeyCode.LEFT, () -> move(-1, 0),
                KeyCode.RIGHT, () -> move(1, 0)
        );
    }

    public void move(KeyEvent keyEvent) {
            Runnable moveAction = movementMap.get(keyEvent.getCode());
            if (moveAction != null) {
                moveAction.run();
            }
    }

    private void move(int deltaX, int deltaY) {
        int newX = xPosition.get() + deltaX;
        int newY = yPosition.get() + deltaY;

        if (grid.isWithinBounds(newX, newY) &&
                (grid.getTile(newY, newX).getType() != TileType.WALL) &&
                grid.getTile(newY, newX) != null) {
            if (movementDelay.isReady()) {
                xPosition.set(newX);
                yPosition.set(newY);
                movementDelay.reset();
            }
        }
    }

    public void updatePosition(int oldX, int oldY, int newX, int newY) {
        if (oldX != newX) {
            Platform.runLater(() -> {
                this.setXPosition(newX);
                grid.getChildren().remove(this);
                grid.add(this, this.getXPosition(), this.getYPosition());
            });
        }

        if (oldY != newY) {
            Platform.runLater(() -> {
                this.setYPosition(newY);
                grid.getChildren().remove(this);
                grid.add(this, this.getXPosition(), this.getYPosition());
            });
        }
    }

    @Override
    public void move() {
        movementDelay.adjustMovementDelay(
                grid.getTile(
                        this.getYPosition(),
                        this.getXPosition()
                ).getMovementSpeed());
    }

    @Override
    public void updateUI() {
        grid.getChildren().remove(this);
        grid.add(this, this.getXPosition(), this.getYPosition());
    }

    public int getXPosition() {
        return xPosition.get();
    }

    public IntegerProperty xPositionProperty() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition.get();
    }

    public IntegerProperty yPositionProperty() {
        return yPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition.set(xPosition);
    }

    public void setYPosition(int yPosition) {
        this.yPosition.set(yPosition);
    }
}
