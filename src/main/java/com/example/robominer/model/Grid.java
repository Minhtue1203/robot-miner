package com.example.robominer.model;

public class Grid {
    private Secteur[][] secteurs;
    private int SIZE = 10;

    public Grid() {
        // Initialiser une grille 10x10 de secteurs
        secteurs = new Secteur[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                secteurs[i][j] = new Empty();
            }
        }
    }

    public int getSize() {
        return this.SIZE;
    }

    public void setSecteur(int row, int col, Secteur secteur) {
        secteurs[row][col] = secteur;
    }

    public Secteur getSecteur(int row, int col) {
        return secteurs[row][col];
    }

    public int getRows() {
        return secteurs.length;
    }

    public int getCols() {
        return secteurs[0].length;
    }

    public boolean isEmpty(int row, int col) {
        return secteurs[row][col] instanceof Empty;
    }

    public Secteur[][] getSecteurs() {
        return secteurs;
    }

    public boolean isPositionValid(int newRow, int newCol) {
        return newRow >= 0 && newRow < getRows() && newCol >= 0 && newCol < getCols();
    }
}
