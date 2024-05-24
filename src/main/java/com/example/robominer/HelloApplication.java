package com.example.robominer;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.Grid;
import com.example.robominer.view.GridView;

public class HelloApplication {
    public static void main(String[] args) {
        Grid grille = new Grid();
        GridView view = new GridView();
        GridController controller = new GridController(grille, view);

        controller.addRandomWater(3); // Ajouter 3 plans d'eau
        controller.addRandomMine(3); // Ajouter 3 mines avec des numéros uniques
        controller.addRandomWarehouse(2); // Ajouter 2 entrepôts avec des numéros uniques
        controller.addRandomRobot(2);

        controller.updateView();
    }
}