package com.example.robominer.controller;

import com.example.robominer.model.*;
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
                mineCounter = mineCounter + 1;
                grid.setSecteur(row, col, new Mine(mineCounter));
                added++;
            }
        }
    }

    public void addRandomWarehouse(int count) {
        int added = 0;
        while (added < count) {
            int row = random.nextInt(grid.getRows());
            int col = random.nextInt(grid.getCols());
            if (grid.isVide(row, col)) {
                warehouseCounter = warehouseCounter + 1;
                grid.setSecteur(row, col, new Warehouse(warehouseCounter));
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
                robotCounter = robotCounter + 1;
                grid.setSecteur(row, col, new Robot(robotCounter));
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

    public void updateView() {
        view.printGrid(grid);
    }
}
