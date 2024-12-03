package com.willy.entity;

import com.willy.Delay;
import com.willy.algorithm.AStar;
import com.willy.algorithm.GraphNode;
import com.willy.grid.Grid;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Chaser extends Rectangle implements Entity {
    private float speedMultiplier = 1.5F;
    private IntegerProperty xPosition = new SimpleIntegerProperty(19);
    private IntegerProperty yPosition = new SimpleIntegerProperty(19);
    private Delay movementDelay = new Delay(0.8);
    private List<GraphNode> route = new ArrayList<>();
    private AStar pathfinder;
    private Player player;
    private Grid grid;

    public Chaser(int size, Player player, Grid grid) {
        super(size, size);
        setStroke(Color.RED);
        setFill(Color.DARKRED);
        this.player = player;
        this.grid = grid;
        this.pathfinder = new AStar(
            grid,
            new GraphNode(xPosition.get(), yPosition.get()),
            new GraphNode(player.getXPosition(), player.getYPosition())
        );
    }

    public void updatePosition(int oldX, int oldY, int newX, int newY) {
        Platform.runLater(() -> {
            if (oldX != newX) {
                this.setXPosition(newX);
                grid.getChildren().remove(this);
                grid.add(this, this.getXPosition(), this.getYPosition());
            }

            if (oldY != newY) {
                this.setYPosition(newY);
                grid.getChildren().remove(this);
                grid.add(this, this.getXPosition(), this.getYPosition());
            }
        });
    }

    @Override
    public void move() {
        route = pathfinder.findPath();
        if (movementDelay.isReady()) {
            if (route != null && !route.isEmpty()) {
                route.removeFirst();
                if (!route.isEmpty()) {
                    xPosition.set(route.getFirst().getXPosition());
                    yPosition.set(route.getFirst().getYPosition());
                }
            }

            movementDelay.adjustMovementDelay(
                    grid.getTile(
                            this.getYPosition(),
                            this.getXPosition()
                    ).getMovementSpeed() * speedMultiplier);

            pathfinder.update(
                    new GraphNode(this.getXPosition(), this.getYPosition()),
                    new GraphNode(player.getXPosition(), player.getYPosition())
            );
        }
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

    public List<GraphNode> getRoute() {
        return route;
    }
}
