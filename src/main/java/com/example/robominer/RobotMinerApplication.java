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
        controller.addRandomMine(2 + random.nextInt(2));
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
        for (int i = 0; i < controller.getRobotCount(); i++) {
            controller.setCurrentRobotIndex(i);
            Node robotNode = graph.getNodeAt(controller.getRobotPosition(i));

            // Determine the next action based on the robot's storage state
            if (controller.getCurrentRobot().getCurrentStorage() < controller.getCurrentRobot().getCapacityStorage()) {
                // Try to move to the closest mine if not fully loaded
                Node closestMine = graph.getClosestMineNode(robotNode, controller.getCurrentRobot().getMineralType());
                if (closestMine != null) {
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
                // Move to the closest warehouse if the storage is full
                Node closestWarehouse = graph.getClosestWarehouseNode(robotNode, controller.getCurrentRobot().getMineralType());
                if (closestWarehouse != null) {
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

            // Update view and console after each move
            view.updateView();
            controller.updateGridConsole();
        }
        controller.nextRobot();
    }
}