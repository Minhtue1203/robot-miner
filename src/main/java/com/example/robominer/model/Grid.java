package com.example.robominer.model;

public class Grid {
    private Secteur[][] grid;

    public Grid() {
        // Initialiser une grille 10x10 de secteurs
        grid = new Secteur[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Vide();
            }
        }
    }

    public void setSecteur(int row, int col, Secteur secteur) {
        grid[row][col] = secteur;
    }

    public Secteur getSecteur(int row, int col) {
        return grid[row][col];
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    public boolean isVide(int row, int col) {
        return grid[row][col] instanceof Vide;
    }
}
