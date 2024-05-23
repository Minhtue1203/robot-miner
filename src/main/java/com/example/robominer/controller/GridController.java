package com.example.robominer.controller;

import com.example.robominer.model.*;
import com.example.robominer.util.MineralType;
import com.example.robominer.view.GridView;
import java.util.Random;

public class GridController {
    private Grid grid;
    private GridView view;
    private Random random;

    private int mineCounter = 0;
    private int warehouseCounter = 0;
    private int robotCounter = 0;

    public GridController(Grid grille, GridView view) {
        this.grid = grille;
        this.view = view;
        this.random = new Random();
    }

    public void addRandomWater(int count) {
        addRandomSecteur(count, new Water());
    }

    public void addRandomMine(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isVide(row, col)) {
                MineralType type = generateMineType(mineCounter);
                int quantity = generateMineQuantity();
                grid.setSecteur(row, col, new Mine(mineCounter++, type, quantity));
                added++;
            }
        }
    }

    private int generateMineQuantity() {
        return 50 + random.nextInt(51);
    }

    private MineralType generateMineType(int count) {
        if (count % 2 == 0) {
            return MineralType.NICKEL;
        } else {
            return MineralType.GOLD;
        }
    }

    public void addRandomWarehouse(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isVide(row, col)) {
                MineralType type = generateMineType(warehouseCounter);
                grid.setSecteur(row, col, new Warehouse(warehouseCounter++, type));
                added++;
            }
        }
    }

    public void addRandomRobot(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isVide(row, col)) {
                MineralType type = generateMineType(robotCounter);
                int capacityStorage = generateCapacityStorage();
                int capacityExtraction = generateCapacityExtraction();
                grid.setSecteur(row, col, new Robot(robotCounter++, type, capacityStorage, capacityExtraction));
                added++;
            }
        }
    }

    private void addRandomSecteur(int count, Secteur sector) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isVide(row, col)) {
                grid.setSecteur(row, col, sector);
                added++;
            }
        }
    }

    private int generateCapacityStorage() {
        return 5 + random.nextInt(11);
    }

    private int generateCapacityExtraction() {
        return 1 + random.nextInt(4);
    }

    public void updateView() {
        view.printGrid(grid);
    }
}
