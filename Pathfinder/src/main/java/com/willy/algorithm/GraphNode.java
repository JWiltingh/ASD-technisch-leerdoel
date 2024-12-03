package com.willy.algorithm;

import java.util.Objects;

public class GraphNode implements Comparable<GraphNode> {
    private int xPosition;
    private int yPosition;
    float cost; // g
    float heuristic; // h
    float totalCost; // f: g + h
    GraphNode parent;

    public GraphNode(int xPosition, int yPosition) {
        this(xPosition, yPosition, 0, 0);
    }

    public GraphNode(int xPosition, int yPosition, float cost, float heuristic) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.cost = cost;
        this.heuristic = heuristic;
        this.totalCost = cost + heuristic;
        this.parent = null;
    }

    @Override
    public int compareTo(GraphNode other) {
        return Float.compare(this.totalCost, other.getTotalCost());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        GraphNode node = (GraphNode) other;
        return xPosition == node.xPosition && yPosition == node.yPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPosition, yPosition);
    }

    @Override
    public String toString() {
        return "GraphNode{" +
            "x=" + xPosition +
            ", y=" + yPosition +
            ", cost=" + cost +
            ", heuristic=" + heuristic +
            ", totalCost=" + getTotalCost() +
            ", parent=" + (parent != null ? "(" + parent.xPosition + ", " + parent.yPosition + ")" : "null") +
            '}';
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
        this.totalCost = cost + heuristic;
    }

    public float getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(float heuristic) {
        this.heuristic = heuristic;
        this.totalCost = cost + heuristic;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public GraphNode getParent() {
        return parent;
    }

    public void setParent(GraphNode parent) {
        this.parent = parent;
    }
}
