package com.willy;

import com.willy.entity.Chaser;
import com.willy.entity.Entity;
import com.willy.entity.Player;
import com.willy.grid.Grid;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    Player player;
    Grid grid;
    List<Entity> entities = new ArrayList<>();
    PathfindingWindow pathfindingWindow;

    @Override
    public void start(Stage primaryStage) {
        grid = new Grid();
        Scene scene = new Scene(grid, grid.getGridWidth(), grid.getGridHeight());

        player = new Player(grid.getCellSize(), grid);
        grid.add(player, player.getXPosition(), player.getYPosition());
        entities.add(player);

        Chaser chaser = new Chaser(grid.getCellSize(), player, grid);
        grid.add(chaser, chaser.getXPosition(), chaser.getYPosition());
        entities.add(chaser);

        scene.setOnKeyPressed(keyEvent -> player.move(keyEvent));

        startUpdateLoop();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Path Finder");
        primaryStage.setResizable(false);
        primaryStage.show();

        pathfindingWindow = new PathfindingWindow(player, chaser);
        pathfindingWindow.init(new Stage());
    }

    private void startUpdateLoop() {
        Task<Void> gameLoop = new Task<>() {
            @Override
            protected Void call() throws Exception {
                long lastTime = System.nanoTime();
                double accumulatedTime = 0;
                double delayTime = 0.1;

                while (!isCancelled()) {
                    long currentTime = System.nanoTime();
                    double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
                    lastTime = currentTime;

                    accumulatedTime += deltaTime;

                    if (accumulatedTime >= delayTime) {
                        accumulatedTime -= delayTime;

                        updateGameState(deltaTime);
                    }
                }

                return null;
            }
        };

        new Thread(gameLoop).start();
    }

    private void updateGameState(double deltaTime) {
        for (Entity entity : entities) {
            entity.move();
        }

        Platform.runLater(() -> {
            for (Entity entity : entities) {
                entity.updateUI();
            }

            pathfindingWindow.showTracer();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
