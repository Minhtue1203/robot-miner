package com.example.robominer;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.Grid;
import com.example.robominer.view.GridView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Random;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Grid grid = new Grid();
        GridView view = new GridView();
        GridController controller = new GridController(grid, view);
        initialize(controller);
        controller.updateGridConsole();
        view.setController(controller);
        view.start(primaryStage);
        System.out.println("Entrez une commande (z = haut, s = bas, q = gauche, d = droite, r = récolter, f = déposer, w = quitter) :");

        view.getScene().setOnKeyPressed(event -> {
            boolean validMove = false;
            System.out.println("Entrez une commande (z = haut, s = bas, q = gauche, d = droite, r = récolter, f = déposer, w = quitter) :");
            switch (event.getCode()) {
                case Z:
                    validMove = controller.moveRobotUp();
                    break;
                case S:
                    validMove = controller.moveRobotDown();
                    break;
                case Q:
                    validMove = controller.moveRobotLeft();
                    break;
                case D:
                    validMove = controller.moveRobotRight();
                    break;
                case R:
                    validMove = controller.harvestResources();
                    break;
                case F:
                    validMove = controller.depositResources();
                    break;
                case W:
                    Platform.exit();
                    break;
                default:
                    break;
            }
            if (validMove) {
                view.updateView();
                controller.updateGridConsole();
                controller.nextRobot();
                view.updateRobotTurnLabel();
            }
        });
    }

    public void initialize(GridController controller) {
        Random random = new Random();
        controller.addRandomWater(1 + random.nextInt(9)); // Ajouter des plans d'eau aléatoires
        controller.addRandomMine(2 + random.nextInt(2)); // Ajouter des mines aléatoires
        controller.addRandomWarehouse(2); // Ajouter des entrepôts aléatoires
        controller.addRandomRobot(2); // Ajouter des robots aléatoires
    }


//    public void playAutoMode(GridView view, GridController controller) {
//        controller.autoPlaceRobot();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}