package com.example.robominer.model;

public class Secteur {
    protected char[][] matrice;
    protected boolean visited;

    public Secteur() {
        matrice = new char[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrice[i][j] = '.';
            }
        }
        this.visited = false;
    }

    public char[][] getMatrice() {
        return matrice;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
