package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Robot extends Secteur {
    private int number;
    private MineralType mineralType;
    private int capacityStorage;
    private int capacityExtraction;
    private int stockActuel;

    public Robot(int number, MineralType mineralType, int capacityStorage, int capacityExtraction) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + number);
        this.capacityStorage = capacityStorage;
        this.capacityExtraction = capacityExtraction;
        this.stockActuel = 0;
        this.mineralType = mineralType;
        this.number = number;
    }

    public void harvest(int amountCollected) {
        this.stockActuel = this.stockActuel + amountCollected;
    }

    public void deposit() {
        // Logique pour déposer
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public int getCapacityStorage() {
        return capacityStorage;
    }

    public int getCapacityExtraction() {
        return capacityExtraction;
    }

    public int getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(int stockActuel) {
        this.stockActuel = stockActuel;
    }

    public int getNumber() {
        return number;
    }
}
