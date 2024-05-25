package com.example.robominer.view;

import com.example.robominer.model.Grid;

public class GridView {
    public void printGrid(Grid grille) {
        for (int i = 0; i < grille.getRows(); i++) {
            for (int row = 0; row < 2; row++) {
                for (int j = 0; j < grille.getCols(); j++) {
                    char[][] matrice = grille.getSecteur(i, j).getMatrice();
                    System.out.print(matrice[row][0] + " " + matrice[row][1] + " ");
                }
                System.out.println();
            }
        }
    }
}
