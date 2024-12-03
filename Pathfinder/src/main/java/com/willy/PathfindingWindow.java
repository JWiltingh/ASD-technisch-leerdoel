package com.willy;

import com.willy.algorithm.GraphNode;
import com.willy.entity.Chaser;
import com.willy.entity.Player;
import com.willy.grid.Grid;
import com.willy.grid.Tile;
import com.willy.grid.TileType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class PathfindingWindow {
    private Player player;
    private Grid grid;
    private Grid tracerGrid;
    private Chaser chaser;

    public PathfindingWindow(Player player, Chaser chaser) {
        this.player = player;
        this.grid = new Grid();
        this.tracerGrid = new Grid();
        this.chaser = chaser;
    }

    public void init(Stage stage) {
        StackPane stackPane = new StackPane(grid, tracerGrid);
        Scene scene = new Scene(stackPane);
        Player target = new Player(grid.getCellSize(), grid);
        Chaser enemy = new Chaser(grid.getCellSize(), player, grid);

        grid.add(target, target.getXPosition(), target.getYPosition());
        grid.add(enemy, enemy.getXPosition(), enemy.getYPosition());

        player.yPositionProperty().addListener((observable, oldValue, newValue) -> target.updatePosition(target.getXPosition(),
                (Integer) oldValue,
                target.getXPosition(),
                (Integer) newValue));

        player.xPositionProperty().addListener((observable, oldValue, newValue) -> target.updatePosition((Integer) oldValue,
                target.getYPosition(),
                (Integer) newValue,
                target.getYPosition()));

        chaser.yPositionProperty().addListener((observable, oldValue, newValue) -> {
            enemy.updatePosition(enemy.getXPosition(),
                    (Integer) oldValue,
                    enemy.getXPosition(),
                    (Integer) newValue);
        });

        chaser.xPositionProperty().addListener((observable, oldValue, newValue) -> {
            enemy.updatePosition((Integer) oldValue,
                    enemy.getYPosition(),
                    (Integer) newValue,
                    enemy.getYPosition());
        });

        stage.setScene(scene);
        stage.setTitle("Pathfinder Window");
        stage.show();
    }

    public void showTracer() {
        Platform.runLater(() -> {
            tracerGrid.getChildren().clear();

            for (GraphNode graphNode : chaser.getRoute()) {
                Tile tile = new Tile(TileType.VISUAL, grid.getCellSize(), graphNode.getXPosition(), graphNode.getYPosition());
                tracerGrid.add(tile, graphNode.getXPosition(), graphNode.getYPosition());
            }

            for (int row = 0; row < grid.getRowCount(); row++) {
                for (int column = 0; column < grid.getColumnCount(); column++) {
                    Tile tile = new Tile(TileType.VISUAL, grid.getCellSize(), column, row);

                    GraphNode graphNode = findGraphNode(chaser.getRoute(), column, row);
                    if (graphNode != null) {
                        if ((player.getXPosition() != graphNode.getXPosition() &&
                            player.getYPosition() != graphNode.getYPosition()) ||
                            (chaser.getXPosition() != graphNode.getXPosition() &&
                            chaser.getYPosition() != graphNode.getYPosition())) {
                            tracerGrid.add(tile, column, row);
                        }

                    } else {
                        tracerGrid.add(tile, column, row);
                        tile.setOpacity(0.0);
                    }
                }
            }

        });
    }

    private GraphNode findGraphNode(List<GraphNode> route, int x, int y) {
        for (GraphNode graphNode : route) {
            if (graphNode.getXPosition() == x && graphNode.getYPosition() == y) {
                return graphNode;
            }
        }
        return null;
    }
}
