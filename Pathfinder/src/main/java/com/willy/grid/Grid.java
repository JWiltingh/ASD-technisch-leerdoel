package com.willy.grid;

import com.willy.entity.Player;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Grid extends GridPane {
    private int[][] grid = {
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 2, 2, 1, 1, 1, 0},
            {0, 1, 0, 2, 2, 2, 2, 1, 0, 0, 0, 1, 0, 1, 2, 0, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 1, 1, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 1, 2, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 1, 1, 1, 3, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 3, 0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 1, 3, 1, 3, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0},
            {0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0},
            {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 2, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0},
    };
    private int rows;
    private int columns;
    private int cellSize = 25;

    public Grid() {
        rows = grid.length;
        columns = grid[0].length;
        createGrid();
    }

    public void createGrid() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int cell = grid[row][column];
                Tile tile = new Tile(TileType.values()[cell], cellSize, rows, columns);
                this.add(tile, column, row);
            }
        }
    }

    private Node getCell(int row, int column) {
        ObservableList<Node> children = this.getChildren();

        for (Node node : children) {
            if(getRowIndex(node) == row && getColumnIndex(node) == column) {
                return node;
            }
        }

        return null;
    }

    public Tile getTile(int row, int column) {
        Node node = getCell(row, column);
        if (node instanceof Tile tile) {
            return tile;
        }

        return null;
    }

    public Player getPlayer(int row, int column) {
        Node node = getCell(row, column);
        if (node instanceof Player player) {
            return player;
        }

        return null;
    }

    public boolean isWithinBounds(int row, int column) {
        return !(row < 0 || row >= rows || column < 0 || column >= columns);
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getGridWidth() {
        return (this.getColumnCount() + 1) * getCellSize();
    }

    public int getGridHeight() {
        return (this.getRowCount() + 1) * getCellSize();
    }
}
