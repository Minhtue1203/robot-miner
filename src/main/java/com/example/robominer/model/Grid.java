package com.example.robominer.model;

public class Grid {
    private Secteur[][] secteurs;

    public Grid() {
        // Initialiser une grille 10x10 de secteurs
        secteurs = new Secteur[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                secteurs[i][j] = new Empty();
            }
        }
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
