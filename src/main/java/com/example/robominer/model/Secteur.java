package com.example.robominer.model;

public class Secteur {
    protected char[][] matrice;

    public Secteur() {
        matrice = new char[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrice[i][j] = '.';
            }
        }
    }

    public char[][] getMatrice() {
        return matrice;
    }
}
