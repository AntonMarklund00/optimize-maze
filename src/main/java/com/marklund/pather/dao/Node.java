package com.marklund.pather.dao;

public class Node {
    private int y;
    private int x;
    private Node[] neighbors;
    private int distance;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
        neighbors = new Node[4];
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Node[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int position, Node neighbor) {
        neighbors[position] = neighbor;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
