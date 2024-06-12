package com.example.robominer;

import com.example.robominer.controller.GridController;
import com.example.robominer.model.Grid;
import com.example.robominer.model.Graph;
import com.example.robominer.model.Node;
import com.example.robominer.view.GridView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class RobotMinerApplication extends Application {
    private GridView view;
    private GridController controller;
    private Graph graph;
    private AnimationTimer timer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Grid grid = new Grid();
        view = new GridView();
        controller = new GridController(grid, view);
        view.setController(controller);
        Random random = new Random();
        controller.addRandomWater(1 + random.nextInt(9));
        controller.addRandomMine(2); // Assurez-vous d'ajouter exactement 2 mines
        controller.addRandomWarehouse(2);
        controller.addRandomRobot(2);

        graph = new Graph(grid);
        controller.setGraph(graph);

        controller.updateGridConsole();
        view.start(primaryStage);

        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 500_000_000) {
                    moveRobots();
                    lastUpdate = now;
                }
            }
        };

        timer.start();
    }

    private void moveRobots() {
        boolean allMinesDepleted = controller.allMinesDepleted();
        boolean allRobotsDeposited = true;

        for (int i = 0; i < controller.getRobotCount(); i++) {
            controller.setCurrentRobotIndex(i);
            Node robotNode = graph.getNodeAt(controller.getRobotPosition(i));

            if (allMinesDepleted) {
                // Toutes les mines sont épuisées, déplacer les robots vers les entrepôts pour déposer les ressources restantes
                Node closestWarehouse = graph.getClosestWarehouseNode(robotNode, controller.getCurrentRobot().getMineralType());
                if (closestWarehouse != null) {
                    controller.resetNodeDistances(); // Réinitialiser les distances des nœuds avant chaque exécution de Dijkstra
                    List<Node> pathToWarehouse = graph.dijkstra(robotNode, closestWarehouse);
                    if (!pathToWarehouse.isEmpty()) {
                        controller.moveRobotAlongPath(pathToWarehouse);
                        boolean depositedResources = controller.depositResources();
                        if (!depositedResources) {
                            System.out.println("Impossible de déposer des ressources pour le robot " + controller.getCurrentRobot().getNumber());
                        }
                    }
                } else {
                    System.out.println("Aucun entrepôt de ce type n'est disponible pour le robot " + controller.getCurrentRobot().getNumber());
                }
            } else {
                // Fonctionnement normal : se déplacer vers la mine si le robot n'est pas plein, sinon se déplacer vers l'entrepôt
                if (controller.getCurrentRobot().getCurrentStorage() < controller.getCurrentRobot().getCapacityStorage()) {
                    Node closestMine = graph.getClosestMineNode(robotNode, controller.getCurrentRobot().getMineralType());
                    if (closestMine != null) {
                        controller.resetNodeDistances(); // Réinitialiser les distances des nœuds avant chaque exécution de Dijkstra
                        List<Node> pathToMine = graph.dijkstra(robotNode, closestMine);
                        if (!pathToMine.isEmpty()) {
                            controller.moveRobotAlongPath(pathToMine);
                            boolean harvestedResources = controller.harvestResources();
                            if (!harvestedResources) {
                                System.out.println("Impossible de récolter des ressources pour le robot " + controller.getCurrentRobot().getNumber());
                            }
                        }
                    } else {
                        System.out.println("Aucune mine de ce type n'est disponible pour le robot " + controller.getCurrentRobot().getNumber());
                    }
                } else {
                    Node closestWarehouse = graph.getClosestWarehouseNode(robotNode, controller.getCurrentRobot().getMineralType());
                    if (closestWarehouse != null) {
                        controller.resetNodeDistances(); // Réinitialiser les distances des nœuds avant chaque exécution de Dijkstra
                        List<Node> pathToWarehouse = graph.dijkstra(robotNode, closestWarehouse);
                        if (!pathToWarehouse.isEmpty()) {
                            controller.moveRobotAlongPath(pathToWarehouse);
                            boolean depositedResources = controller.depositResources();
                            if (!depositedResources) {
                                System.out.println("Impossible de déposer des ressources pour le robot " + controller.getCurrentRobot().getNumber());
                            }
                        }
                    } else {
                        System.out.println("Aucun entrepôt de ce type n'est disponible pour le robot " + controller.getCurrentRobot().getNumber());
                    }
                }
            }

            if (controller.getCurrentRobot().getCurrentStorage() > 0) {
                allRobotsDeposited = false;
            }

            // Mettre à jour la vue et la console après chaque déplacement
            view.updateView();
            controller.updateGridConsole();
        }

        if (allMinesDepleted && allRobotsDeposited) {
            timer.stop();
            System.out.println("Tous les robots ont déposé les ressources. Simulation terminée.");
        }

        controller.nextRobot();
        view.updateRobotTurnLabel(); // S'assurer que le label est mis à jour après le changement de tour
    }
}
