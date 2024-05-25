package com.example.robominer.view;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.*;
import com.example.robominer.util.Helper;
import com.example.robominer.util.MineralType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class GridView extends Application {
    private GridController controller;
    private GridPane gridPane;
    private static final int SECTEUR_SIZE = 50;
    private Scene scene;

    public void setController(GridController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulation de robot miner");
        gridPane = new GridPane();
        scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        updateView();
    }

    public Scene getScene() {
        return scene;
    }

    public void updateView() {
        gridPane.getChildren().clear();
        Secteur[][] secteurs = controller.getGrid().getSecteurs();

        for (int row = 0; row < secteurs.length; row++) {
            for (int col = 0; col < secteurs[row].length; col++) {
                Secteur secteur = secteurs[row][col];
                Color color;
                if (secteur instanceof Water) {
                    color = Color.BLUE;
                } else if (secteur instanceof Warehouse) {
                    color = Color.BLACK;
                } else if (secteur instanceof Mine) {
                    Mine mine = (Mine) secteur;
                    color = Helper.getColorMine((mine));
            } else if (secteur instanceof Robot) {
                    Robot robot = (Robot) secteur;
                    color = robot.getColor();
                } else {
                    color = Color.WHITE;
                }

                Rectangle rect = new Rectangle(SECTEUR_SIZE, SECTEUR_SIZE, color);
                rect.setStroke(Color.BLACK); // Pour les bordures
                gridPane.add(rect, col, row);
            }
        }
    }

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
