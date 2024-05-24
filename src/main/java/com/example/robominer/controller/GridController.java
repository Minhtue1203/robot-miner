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
                Robot robot =  new Robot(number, type, capacityStorage, capacityExtraction);
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
        int robotRow = robotPosition[0];
        int robotCol = robotPosition[1];
        if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getCols()) {
            Secteur target = grid.getSecteur(newRow, newCol);
            if(target instanceof Empty || target instanceof Mine || target instanceof Warehouse) {
                grid.setSecteur(robotRow, robotCol, new Empty());
                robotPosition[0] = newRow;
                robotPosition[1] = newCol;
                if (target instanceof Mine) {
                    Mine mine = (Mine) target;
                    mine.setRobot(getCurrentRobot());
                    grid.setSecteur(newRow, newCol, mine);
                } else if (target instanceof Warehouse) {
                    Warehouse warehouse = (Warehouse) target;
                    warehouse.setRobot(getCurrentRobot());
                    grid.setSecteur(newRow, newCol, warehouse);
                } else {
                    grid.setSecteur(newRow, newCol, getCurrentRobot());
                }
                updateAddedSecteurs(robotRow, robotCol, newRow, newCol);
                return true;
            } else if (target instanceof Water) {
                System.out.println("Le robot ne peut pas se déplacer dans l'eau.");
            }
        }
        return false;
    }

    private void updateAddedSecteurs(int oldRow, int oldCol, int newRow, int newCol) {
        for (SecteurInfo secteurInfo : addedSecteurs) {
            if (secteurInfo.getType().equals(SecteurType.ROBOT) && secteurInfo.getRow() == oldRow && secteurInfo.getCol() == oldCol) {
                secteurInfo.setRow(newRow);
                secteurInfo.setCol(newCol);
                break;
            }
        }
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

    public void nextRobot() {
        currentRobotIndex = (currentRobotIndex + 1) % robotPositions.size();
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
                int harvested = Math.min(robot.getCapacityExtraction(), mine.getQuantity());
                mine.setQuantity(mine.getQuantity() - harvested);
                System.out.println("Robot " + robot.getNumber() + " a récolté " + harvested + " unités de " + mine.getType());
                if (mine.getQuantity() == 0) {
                    System.out.println("La mine " + mine.getNumber() + " est épuisée.");
                }
                return true;
            } else {
                System.out.println("Le robot n'est pas le même type de la mine. Vous ne pouvez pas récolter.");
            }
        } else {
            System.out.println("Le robot n'est pas dans une mine.");
        }
        return false;
    }

    public void updateView() {
        view.printGrid(grid);
        System.out.println();
        System.out.println();
        secteurInfoView.printSecteurInfo(addedSecteurs);
    }
}
