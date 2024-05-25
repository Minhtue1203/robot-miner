package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Warehouse extends Secteur {
    private MineralType mineralType;
    private int storedResources;
    private int number;

    public Warehouse(int warehouseNumber, MineralType mineralType) {
        matrice[0][0] = 'E';
        matrice[0][1] = (char) ('0' + warehouseNumber);
        this.mineralType = mineralType;
        this.storedResources = 0;
        this.number = warehouseNumber;
    }

    public void addResources(int amount) {
        this.storedResources += amount;
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public int getStoredResources() {
        return storedResources;
    }

    public void addRobot(Robot robot) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + robot.getNumber());
    }

    public void removeRobot() {
        matrice[1][0] = '.';
        matrice[1][1] = '.';
    }

    public int getNumber() {
        return number;
    }
}
