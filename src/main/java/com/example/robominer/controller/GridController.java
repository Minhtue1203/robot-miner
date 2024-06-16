package com.example.robominer.controller;

import com.example.robominer.model.*;
import com.example.robominer.util.MineralType;
import com.example.robominer.util.SecteurType;
import com.example.robominer.util.StatusRobotType;
import com.example.robominer.view.GridView;
import com.example.robominer.view.SecteurInfoView;

import java.util.*;

import static com.example.robominer.util.Helper.*;

public class GridController {
    private Grid grid;
    private GridView view;
    private SecteurInfoView secteurInfoView;
    private Random random;

    private Map<Integer, List<int[]>> depositPaths;

    private int mineCounter = 0;
    private int warehouseCounter = 0;
    private int robotCounter = 0;

    private List<SecteurInfo> addedSecteurs;
    private int currentRobotIndex;
    private List<Robot> robots;
    private List<Mine> mines;

    public GridController(Grid grid, GridView view) {
        this.grid = grid;
        this.view = view;
        this.random = new Random();
        this.addedSecteurs = new ArrayList<>();
        this.secteurInfoView = new SecteurInfoView();
        // this.robotPositions = new ArrayList<>();
        this.currentRobotIndex = 0;
        this.robots = new ArrayList<>();
        this.mines = new ArrayList<>();
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
                Mine mine = new Mine(number, type, quantity);
                grid.setSecteur(row, col, mine);
                mines.add(mine);
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
                Warehouse warehouse = new Warehouse(number, type);
                grid.setSecteur(row, col, warehouse);
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
                Robot robot =  new Robot(row, col, number, type, capacityStorage, capacityExtraction);
                grid.setSecteur(row, col, robot);
                grid.getSecteur(row, col).setVisited(true); // Marquer le secteur initial du robot comme visité
                added++;
                robots.add(robot);
                addedSecteurs.add(new SecteurInfo(SecteurType.ROBOT, number, row, col, type, 0, capacityStorage));
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

    public boolean moveRobot(int[] robotPosition, int newX, int newY) {
        int currentX = robotPosition[0];
        int currentY = robotPosition[1];

        if (grid.isPositionValid(newX, newY)) {
            Secteur target = grid.getSecteur(newX, newY);
            if(target instanceof Empty || target instanceof Mine || target instanceof Warehouse) {
                Secteur original = grid.getSecteur(currentX, currentY);
                if (original instanceof Mine) {
                    Mine mineOriginal = (Mine) original;
                    mineOriginal.removeRobot();
                    grid.setSecteur(currentX, currentY, mineOriginal); // La mine reste visible
                } else if (original instanceof Warehouse) {
                    Warehouse warehouseOriginal = (Warehouse) original;
                    warehouseOriginal.removeRobot();
                    grid.setSecteur(currentX, currentY, warehouseOriginal);
                } else {
                    grid.setSecteur(currentX, currentY, new Empty());
                }

                if (target instanceof Mine) {
                    Mine mine = (Mine) target;
                    mine.addRobot(getCurrentRobot());
                    grid.setSecteur(newX, newY, mine);
                } else if (target instanceof Warehouse) {
                    Warehouse warehouse = (Warehouse) target;
                    warehouse.addRobot(getCurrentRobot());
                    grid.setSecteur(newX, newY, warehouse);
                } else {
                    grid.setSecteur(newX, newY, getCurrentRobot());
                }

                // Marquer le secteur comme visité
                target.setVisited(true);
                grid.getSecteur(newX, newY).setVisited(true);
                markAdjacentSectorsAsVisited(newX, newY);

                updateRobotSecteurInfo(currentX, currentY, newX, newY);
                getCurrentRobot().updatePosition(newX, newY);
                return true;
            } else if (target instanceof Water) {
                System.out.println("Le robot ne peut pas se déplacer dans l'eau.");
            }
        }
        return false;
    }

    private void markAdjacentSectorsAsVisited(int x, int y) {
        int[][] directions = {
                {0, 1}, {1, 0}, {0, -1}, {-1, 0}, // est, sud, ouest, nord
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (grid.isPositionValid(newX, newY)) {
                grid.getSecteur(newX, newY).setVisited(true);
            }
        }
    }



    public boolean moveRobotUp() {
        int[] robotPosition = getCurrentRobot().getCurrentPosition();
        return moveRobot(robotPosition, robotPosition[0] - 1, robotPosition[1]);
    }

    public boolean moveRobotDown() {
        int[] robotPosition = getCurrentRobot().getCurrentPosition();
        return moveRobot(robotPosition, robotPosition[0] + 1, robotPosition[1]);
    }

    public boolean moveRobotLeft() {
        int[] robotPosition = getCurrentRobot().getCurrentPosition();
        return moveRobot(robotPosition, robotPosition[0], robotPosition[1] - 1);
    }

    public boolean moveRobotRight() {
        int[] robotPosition = getCurrentRobot().getCurrentPosition();
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
        return robots.get(currentRobotIndex);
    }
    public boolean harvestResources() {
        // int[] robotPosition = robot.getCurrentPosition();
        int[] robotPosition = getCurrentRobot().getCurrentPosition();
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

    public boolean depositResources() {
        int[] robotPosition = robots.get(currentRobotIndex).getCurrentPosition();
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
            }
        } else {
            System.out.println("Le robot n'est pas dans un entrepôt.");
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
                secteurInfo.setCurrentStock(0);
            }
            if (secteurInfo.getType().equals(SecteurType.WAREHOUSE) && secteurInfo.getNumber() == warehouseId) {
                secteurInfo.setCurrentStock(storedAmount);
                // Peut-être ajouter une méthode pour mettre à jour les ressources de l'entrepôt si nécessaire
            }
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void updateGridConsole() {
        view.printGrid(grid);
        secteurInfoView.printSecteurInfo(addedSecteurs);
    }

    public List<SecteurInfo> getAddedSecteurs() {
        return addedSecteurs;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void nextRobot() {
        // currentRobotIndex = (currentRobotIndex + 1) % robotPositions.size();
        System.out.println("C'est le tour du Robot " + getCurrentRobot().getNumber());
        currentRobotIndex = (currentRobotIndex + 1) % robots.size();
    }

    public void autoPlaceRobotsForMine() {
        if(isMineExhausted()) {
            System.out.println("All mine is exhausted. Can't find path");
            return;
        }
        AutoMine autoMine = new AutoMine(this.grid);
        for (Robot robot : robots) {
            if (robot.getStatus() == StatusRobotType.FINDING) {
                List<int[]> path = autoMine.findPathToNearestMine(robot);
                if(path.size() > 0) {
                    path.removeFirst();
                }
                robot.setRobotPaths(path);
                robot.setCurrentIndexPath(0);
            }
        }
    }

    public void autoPlaceRobotsForDeposit() {
        AutoMine autoMine = new AutoMine(this.grid);
        for (Robot robot : robots) {
            if (robot.getStatus() == StatusRobotType.DEPOSITING) {
                List<int[]> path = autoMine.findPathToNearestMine(robot);
                path.removeFirst(); // remove current position
                robot.setRobotPaths(path);
                robot.setCurrentIndexPath(0);
            }
        }
    }

    public boolean isMineExhausted() {
        boolean isExhausted = true;
        for (Mine mine: mines) {
            if(mine.getQuantity() > 0) {
                return false;
            }
        }
        return isExhausted;
    }

    public void loopHavestResources(Robot robot) {
        int[] robotPosition = robot.getCurrentPosition();
        Secteur secteur = grid.getSecteur(robotPosition[0], robotPosition[1]);

        if(!(secteur instanceof Mine)) {
            System.out.printf("Le robot n'est pas dans une mine: %d %d%n", robotPosition[0], robotPosition[1]);
            return;
        }

        Mine mine = (Mine) secteur;

        if (robot.getMineralType() != mine.getMineralType()) {
            System.out.println("Le robot n'est pas le même type de la mine. Vous ne pouvez pas récolter.");
            return;
        }

        if (robot.getCurrentStorage() == robot.getCapacityStorage()) {
            System.out.println("Le robot est plein");
            switchRobotFor(robot, StatusRobotType.DEPOSITING);
            updateSecteurInfoAfterHarvest(robot.getNumber(), mine.getNumber(), robot.getCurrentStorage(), mine.getQuantity());
            return;
        }

        int availableSpace = robot.getCapacityStorage() - robot.getCurrentStorage();
        int possibleExtraction = Math.min(robot.getCapacityExtraction(), mine.getQuantity());
        int actualExtraction = Math.min(possibleExtraction, availableSpace);
        int remainingQuality = mine.getQuantity() - actualExtraction;
        mine.setQuantity(mine.getQuantity() - actualExtraction);
        robot.addStorage(actualExtraction);
        mines.get(mine.getNumber()-1).setQuantity(remainingQuality);
        System.out.println("Robot " + robot.getNumber() + " a récolté " + actualExtraction + " unités de " + mine.getType());

        if (mine.isEmpty()) {
            System.out.println("La mine " + mine.getNumber() + " est épuisée.");
            grid.setSecteur(robotPosition[0], robotPosition[1], robot);
            switchRobotFor(robot, StatusRobotType.DEPOSITING);
            updateSecteurInfoAfterHarvest(robot.getNumber(), mine.getNumber(), robot.getCurrentStorage(), mine.getQuantity());
            return;
        }
        updateSecteurInfoAfterHarvest(robot.getNumber(), mine.getNumber(), robot.getCurrentStorage(), mine.getQuantity());
    }

    public void loopDepositResources(Robot robot) {
        System.out.println("loopDepositResources");
        int[] robotPosition = robot.getCurrentPosition();
        Secteur secteur = grid.getSecteur(robotPosition[0], robotPosition[1]);
        if(!(secteur instanceof Warehouse)) {
            System.out.println("Le robot n'est pas dans une warehouse.");
            return;
        }
        Warehouse warehouse = (Warehouse) secteur;

        if (robot.getMineralType() != warehouse.getMineralType()) {
            System.out.println("Le robot n'est pas le même type de l'entrepot. Vous ne pouvez pas déposer.");
            return;
        }

        int storedAmount = robot.getCurrentStorage();
        warehouse.addResources(storedAmount);

        robot.setCurrentStorage(0);
        System.out.println("Robot " + robot.getNumber() + " a déposé " + storedAmount + " unités de ressources dans l'entrepôt " + warehouse.getNumber());
        switchRobotFor(robot, StatusRobotType.FINDING);
        updateSecteurInfoAfterDeposit(robotPosition[0], robotPosition[1], robot.getNumber(), warehouse.getNumber(), warehouse.getStoredResources());
    }

    private void switchRobotFor(Robot robot, StatusRobotType type) {
        int index = robot.getNumber() - 1;
        robots.get(index).setStatus(type);
        if(type == StatusRobotType.FINDING) {
            autoPlaceRobotsForMine();
        } else if (type == StatusRobotType.DEPOSITING) {
            autoPlaceRobotsForDeposit();
        }
    }
}
