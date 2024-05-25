package com.example.robominer.model;

public class Water extends Secteur {
    public Water() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrice[i][j] = 'X';
            }
        }
    }
}
