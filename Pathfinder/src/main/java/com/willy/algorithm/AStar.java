package com.willy.algorithm;

import com.willy.grid.Grid;
import com.willy.grid.Tile;
import com.willy.grid.TileType;

import java.util.*;

public class AStar {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private Grid grid;
    private GraphNode startNode;
    private GraphNode endNode;

    public AStar(Grid grid, GraphNode startNode, GraphNode endNode) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public void update(GraphNode startNode, GraphNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    private int heuristic(int startX, int startY, int endX, int endY) {
        return Math.abs(startX - endX) + Math.abs(startY - endY);
    }

    private boolean isValid(int x, int y) {
        if (x < 0 || x >= grid.getColumnCount()) {
            return false;
        }
        if (y < 0 || y >= grid.getRowCount()) {
            return false;
        }
        if (grid.getTile(y, x).getType() == TileType.WALL) {
            return false;
        }

        return true;
    }

    public List<GraphNode> findPath() {
        PriorityQueue<GraphNode> openSet = new PriorityQueue<>();
        Set<GraphNode> closedSet = new HashSet<>();

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            GraphNode currentNode = openSet.poll();

            if (currentNode.equals(endNode)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode);

            for (GraphNode neighbor: getNeighbors(currentNode)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                float tentativeCost = currentNode.getCost() + neighbor.getCost();

                if (!openSet.contains(neighbor) || tentativeCost < neighbor.getCost()) {
                    neighbor.setParent(currentNode);
                    neighbor.setCost(tentativeCost);
                    neighbor.setHeuristic(heuristic(neighbor.getXPosition(), neighbor.getYPosition(), endNode.getXPosition(), endNode.getYPosition()));
                    neighbor.setTotalCost(neighbor.getCost() + neighbor.getHeuristic());

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return List.of();
    }

    private List<GraphNode> reconstructPath(GraphNode currentNode) {
        List<GraphNode> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    private List<GraphNode> getNeighbors(GraphNode node) {
        List<GraphNode> neighbors = new ArrayList<>();
        int x = node.getXPosition();
        int y = node.getYPosition();

        for (int[] direction : DIRECTIONS) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (isValid(newX, newY)) {
                Tile tile = grid.getTile(newY, newX);
                GraphNode newNode = new GraphNode(newX, newY, tile.getMovementSpeed(), heuristic(newX, newY, x, y));
                newNode.setParent(node);
                neighbors.add(newNode);
            }
        }
        return neighbors;
    }
}
