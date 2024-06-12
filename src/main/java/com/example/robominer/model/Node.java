package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Node {
    private int row;
    private int col;
    private Secteur secteur;
    private int distance;
    private Node previousNode;

    public Node(int row, int col, Secteur secteur) {
        this.row = row;
        this.col = col;
        this.secteur = secteur;
        this.distance = Integer.MAX_VALUE;
        this.previousNode = null;
    }

    // Getters et setters

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public boolean isWalkable() {
        return !(secteur instanceof Water);
    }

    public MineralType getMineralType() {
        if (secteur instanceof Mine) {
            return ((Mine) secteur).getMineralType();
        } else if (secteur instanceof Warehouse) {
            return ((Warehouse) secteur).getMineralType();
        }
        return null;
    }
}