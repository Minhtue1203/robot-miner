package com.example.robominer.controller;

import com.example.robominer.model.*;
import com.example.robominer.util.MineralType;
import com.example.robominer.util.SecteurType;
import com.example.robominer.view.GridView;
import com.example.robominer.view.SecteurInfoView;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import static com.example.robominer.util.Helper.*;

public class GridController {
    private Grid grid;
    private GridView view;
    private SecteurInfoView secteurInfoView;
    private Random random;
    private Graph graph;
    private int mineCounter = 0;
    private int warehouseCounter = 0;
    private int robotCounter = 0;

    private List<SecteurInfo> addedSecteurs;
    private List<int[]> robotPositions;
    private int currentRobotIndex;
    private List<Robot> addedRobots;

    public GridController(Grid grille, GridView view) {
        this.grid = grille;
        this.view = view;
        this.random = new Random();
        this.addedSecteurs = new ArrayList<>();
        this.secteurInfoView = new SecteurInfoView();
        this.robotPositions = new ArrayList<>();
        this.currentRobotIndex = 0;
        this.addedRobots = new ArrayList<>();
        this.graph = new Graph(grid);
    }

    public void setGraph(Graph graph) { // Nouvelle méthode
        this.graph = graph;
    }
    public void addRandomWater(int count) {
        addRandomSecteur(count, new Water());
    }

    public void addRandomMine(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isEmpty(row, col)) {
                MineralType type = generateMineType(mineCounter);
                int quantity = generateMineQuantity();
                mineCounter++;
                int number = mineCounter;
                grid.setSecteur(row, col, new Mine(number, type, quantity));
                added++;
                addedSecteurs.add(new SecteurInfo(SecteurType.MINE, number, row, col, type, quantity, quantity));
            }
        }
    }

    public void addRandomWarehouse(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isEmpty(row, col)) {
                MineralType type = generateMineType(warehouseCounter);
                warehouseCounter++;
                int number = warehouseCounter;
                grid.setSecteur(row, col, new Warehouse(number, type));
                added++;
                addedSecteurs.add(new SecteurInfo(SecteurType.WAREHOUSE, number, row, col, type, 0));
            }
        }
    }

    public void addRandomRobot(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isEmpty(row, col)) {
                MineralType type = generateMineType(robotCounter);
                int capacityStorage = generateCapacityStorage();
                int capacityExtraction = generateCapacityExtraction();
                robotCounter++;
                int number = robotCounter;
                Robot robot = new Robot(number, type, capacityStorage, capacityExtraction);
                grid.setSecteur(row, col, robot);
                added++;
                addedRobots.add(robot);
                addedSecteurs.add(new SecteurInfo(SecteurType.ROBOT, number, row, col, type, 0, capacityStorage));
                robotPositions.add(new int[]{row, col});
            }
        }
    }


    private void addRandomSecteur(int count, Secteur sector) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isEmpty(row, col)) {
                grid.setSecteur(row, col, sector);
                added++;
            }
        }
    }

    public boolean moveRobot(int[] robotPosition, int newRow, int newCol) {
        int currentRow = robotPosition[0];
        int currentCol = robotPosition[1];
        if (grid.isPositionValid(newRow, newCol)) {
            Secteur target = grid.getSecteur(newRow, newCol);
            if(target instanceof Empty || target instanceof Mine || target instanceof Warehouse) {

                Secteur original = grid.getSecteur(currentRow, currentCol);

                if (original instanceof Mine) {
                    Mine mineOriginal = (Mine) original;
                    mineOriginal.removeRobot();
                    grid.setSecteur(currentRow, currentCol, mineOriginal); // La mine reste visible
                } else if (original instanceof Warehouse) {
                    Warehouse warehouseOriginal = (Warehouse) original;
                    warehouseOriginal.removeRobot();
                    grid.setSecteur(currentRow, currentCol, warehouseOriginal);
                } else {
                    grid.setSecteur(currentRow, currentCol, new Empty());
                }

                robotPosition[0] = newRow;
                robotPosition[1] = newCol;

                if (target instanceof Mine) {
                    Mine mine = (Mine) target;
                    mine.addRobot(getCurrentRobot());
                    grid.setSecteur(newRow, newCol, mine);
                } else if (target instanceof Warehouse) {
                    Warehouse warehouse = (Warehouse) target;
                    warehouse.addRobot(getCurrentRobot());
                    grid.setSecteur(newRow, newCol, warehouse);
                } else {
                    grid.setSecteur(newRow, newCol, getCurrentRobot());
                }
                updateRobotSecteurInfo(currentRow, currentCol, newRow, newCol);
                System.out.println("Robot " + getCurrentRobot().getNumber() + " moved to (" + newRow + ", " + newCol + ")");
                return true;
            } else if (target instanceof Water) {
                System.out.println("Le robot ne peut pas se déplacer dans l'eau.");
            }
        }
        return false;
    }


    public boolean moveRobotUp() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        return moveRobot(robotPosition, robotPosition[0] - 1, robotPosition[1]);
    }

    public boolean moveRobotDown() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        return moveRobot(robotPosition, robotPosition[0] + 1, robotPosition[1]);
    }

    public boolean moveRobotLeft() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        return moveRobot(robotPosition, robotPosition[0], robotPosition[1] - 1);
    }

    public boolean moveRobotRight() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        return moveRobot(robotPosition, robotPosition[0], robotPosition[1] + 1);
    }

    private void updateRobotSecteurInfo(int oldRow, int oldCol, int newRow, int newCol) {
        for (SecteurInfo secteurInfo : addedSecteurs) {
            if (secteurInfo.getType().equals(SecteurType.ROBOT) && secteurInfo.getRow() == oldRow && secteurInfo.getCol() == oldCol) {
                secteurInfo.setRow(newRow);
                secteurInfo.setCol(newCol);
                break;
            }
        }
    }

    public Robot getCurrentRobot() {
        return addedRobots.get(currentRobotIndex);
    }

    public int getCurrentRobotIndex() {
        return currentRobotIndex;
    }

    public boolean harvestResources() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        Secteur secteur = grid.getSecteur(robotPosition[0], robotPosition[1]);
        if (secteur instanceof Mine) {
            Mine mine = (Mine) secteur;
            Robot robot = getCurrentRobot();
            if (robot.getMineralType() == mine.getMineralType()) {
                int availableSpace = robot.getCapacityStorage() - robot.getCurrentStorage();
                int possibleExtraction = Math.min(robot.getCapacityExtraction(), mine.getQuantity());
                int actualExtraction = Math.min(possibleExtraction, availableSpace);

                mine.setQuantity(mine.getQuantity() - actualExtraction);
                robot.addStorage(actualExtraction);
                System.out.println("Robot " + robot.getNumber() + " a récolté " + actualExtraction + " unités de " + mine.getType());
                if (mine.isEmpty()) {
                    System.out.println("La mine " + mine.getNumber() + " est épuisée.");
                }

                updateSecteurInfoAfterHarvest(robot.getNumber(), mine.getNumber(), robot.getCurrentStorage(), mine.getQuantity());
                return true;
            } else {
                System.out.println("Le robot n'est pas le même type de la mine. Vous ne pouvez pas récolter.");
            }
        } else {
            System.out.println("Le robot n'est pas dans une mine.");
        }
        return false;
    }



    private void updateSecteurInfoAfterHarvest(int robotId, int mineId, int amountCollected,  int remainingQuantity) {
        for (SecteurInfo secteurInfo : addedSecteurs) {
            if (secteurInfo.getType().equals(SecteurType.ROBOT) && secteurInfo.getNumber() == robotId) {
                secteurInfo.setCurrentStock(amountCollected);
            }
            if (secteurInfo.getType().equals(SecteurType.MINE) && secteurInfo.getNumber() == mineId) {
                secteurInfo.setCurrentStock(remainingQuantity);
            }
        }
    }

    private void updateSecteurInfoAfterDeposit(int row, int col, int robotId, int warehouseId, int storedAmount) {
        for (SecteurInfo secteurInfo : addedSecteurs) {
            if (secteurInfo.getType().equals(SecteurType.ROBOT) && secteurInfo.getNumber() == robotId) {
                secteurInfo.setCurrentStock(0);  // Réinitialiser le stock du robot à 0 après le dépôt
            }
            if (secteurInfo.getType().equals(SecteurType.WAREHOUSE) && secteurInfo.getNumber() == warehouseId) {
                secteurInfo.setCurrentStock(secteurInfo.getCurrentStock() + storedAmount); // Ajouter les ressources déposées à l'entrepôt
            }
        }
    }
    public boolean depositResources() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        Secteur secteur = grid.getSecteur(robotPosition[0], robotPosition[1]);
        if (secteur instanceof Warehouse) {
            Warehouse warehouse = (Warehouse) secteur;
            Robot robot = getCurrentRobot();
            if (robot.getMineralType() == warehouse.getMineralType()) {
                int storedAmount = robot.getCurrentStorage();
                warehouse.addResources(storedAmount);
                robot.setCurrentStorage(0);

                System.out.println("Robot " + robot.getNumber() + " a déposé " + storedAmount + " unités de ressources dans l'entrepôt " + warehouse.getNumber());

                // Mise à jour de SecteurInfo pour le robot et l'entrepôt
                updateSecteurInfoAfterDeposit(robotPosition[0], robotPosition[1], robot.getNumber(), warehouse.getNumber(), storedAmount);
                return true;
            } else {
                System.out.println("Le robot ne peut pas déposer des ressources dans cet entrepôt.");
            }
        } else {
            System.out.println("Le robot n'est pas dans un entrepôt.");
        }
        return false;
    }


//    public void updateViewConsole() {
//        view.printGrid(grid);
//    }

    public Grid getGrid() {
        return grid;
    }


    public void updateGridConsole() {
        view.printGrid(grid);
        System.out.println();
        secteurInfoView.printSecteurInfo(addedSecteurs);
    }

    public void nextRobot() {
        currentRobotIndex = (currentRobotIndex + 1) % robotPositions.size();
        System.out.println("C'est le tour de deux Robot " );
    }

    public boolean moveRobotToWarehouse() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        Node robotNode = graph.getNodeAt(robotPosition);
        MineralType mineralType = getCurrentRobot().getMineralType();

        Node closestWarehouse = graph.getClosestWarehouseNode(robotNode, mineralType);

        if (closestWarehouse != null) {
            resetNodeDistances(); // Reset node distances before each Dijkstra run
            List<Node> path = graph.dijkstra(robotNode, closestWarehouse);
            moveRobotAlongPath(path);
            return depositResources();
        } else {
            System.out.println("Aucun entrepôt de ce type n'est disponible.");
            return false;
        }
    }

    public boolean moveRobotToMine() {
        int[] robotPosition = robotPositions.get(currentRobotIndex);
        Node robotNode = graph.getNodeAt(robotPosition);
        MineralType mineralType = getCurrentRobot().getMineralType();

        Node closestMine = graph.getClosestMineNode(robotNode, mineralType);

        if (closestMine != null) {
            resetNodeDistances(); // Reset node distances before each Dijkstra run
            List<Node> path = graph.dijkstra(robotNode, closestMine);
            moveRobotAlongPath(path);
            return harvestResources();
        } else {
            System.out.println("Aucune mine de ce type n'est disponible.");
            return false;
        }
    }




    public void moveRobotAlongPath(List<Node> path) {
        for (Node node : path) {
            int[] robotPosition = robotPositions.get(currentRobotIndex);
            int newRow = node.getRow();
            int newCol = node.getCol();
            moveRobot(robotPosition, newRow, newCol);
            view.updateView();  // Mettre à jour la vue après chaque mouvement
        }
    }



    public int getRobotCount() {
        return robotPositions.size();
    }

    public int[] getRobotPosition(int index) {
        return robotPositions.get(index);
    }

    public void setCurrentRobotIndex(int currentRobotIndex) {
        this.currentRobotIndex = currentRobotIndex;
    }

    public void resetNodeDistances() {
        graph.resetNodeDistances();
    }

    public boolean allMinesDepleted() {
        for (SecteurInfo secteurInfo : addedSecteurs) {
            if (secteurInfo.getType().equals(SecteurType.MINE) && secteurInfo.getCurrentStock() > 0) {
                return false;
            }
        }
        return true;
    }








}
