package com.example.robominer;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.Grid;
import com.example.robominer.view.GridView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Random;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Grid grid = new Grid();
        GridView view = new GridView();
        GridController controller = new GridController(grid, view);
        view.setController(controller);
        Random random = new Random();
        controller.addRandomWater( 1 + random.nextInt(9)); // Ajouter 3 plans d'eau
        controller.addRandomMine(2 + random.nextInt(2)); // Ajouter 3 mines avec des numéros uniques
        controller.addRandomWarehouse(2); // Ajouter 2 entrepôts avec des numéros uniques
        controller.addRandomRobot(2);

        controller.updateGridConsole();
        view.start(primaryStage);

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
}

//public class HelloApplication {
//    public static void main(String[] args) {
//        Grid grille = new Grid();
//        GridView view = new GridView();
//        GridController controller = new GridController(grille, view);
//
//        controller.addRandomWater(3); // Ajouter 3 plans d'eau
//        controller.addRandomMine(3); // Ajouter 3 mines avec des numéros uniques
//        controller.addRandomWarehouse(2); // Ajouter 2 entrepôts avec des numéros uniques
//        controller.addRandomRobot(2);
//
//        controller.updateView();
//
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("Robot " + (controller.getCurrentRobotIndex() + 1) + " à déplacer.");
//            boolean validMove = false;
//            while (!validMove) {
//                System.out.println("Entrez une commande (z = haut, s = bas, q = gauche, d = droite, r = récolter, f = déposer, w = quitter) :");
//                String command = scanner.nextLine();
//                if (command.equals("w")) {
//                    return; // quitter l'application
//                }
//
//                switch (command) {
//                    case "z":
//                        validMove = controller.moveRobotUp();
//                        break;
//                    case "s":
//                        validMove = controller.moveRobotDown();
//                        break;
//                    case "q":
//                        validMove = controller.moveRobotLeft();
//                        break;
//                    case "d":
//                        validMove = controller.moveRobotRight();
//                        break;
//                    case "r":
//                        validMove = controller.harvestResources();
//                        break;
//                    case "f":
//                        validMove = controller.depositResources();
//                        break;
//                    default:
//                        System.out.println("Commande inconnue. Utilisez z, s, q, d pour déplacer le robot, w pour quitter.");
//                }
//
//                if (!validMove) {
//                    System.out.println("Le robot ne peut pas se déplacer dans cette direction. Veuillez essayer une autre commande.");
//                }
//            }
//
//            // Mettre à jour l'affichage de la grille
//            controller.updateView();
//
//            // Passer au robot suivant
//            controller.nextRobot();
//        }
//    }
//}