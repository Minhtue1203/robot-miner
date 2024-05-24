package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Robot extends Secteur {
    private int number;
    private MineralType mineralType;
    private int capacityStorage;
    private int capacityExtraction;
    private int currentStorage;

    public Robot(int number, MineralType mineralType, int capacityStorage, int capacityExtraction) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + number);
        this.capacityStorage = capacityStorage;
        this.capacityExtraction = capacityExtraction;
        this.currentStorage = 0;
        this.mineralType = mineralType;
        this.number = number;
    }

    public void addStorage(int amount) {
        this.currentStorage += amount;
    }

    public boolean hasStorageSpace() {
        return this.currentStorage < this.capacityStorage;
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

    public int getNumber() {
        return number;
    }

    public int getCurrentStorage() {
        return currentStorage;
    }

    public void setCurrentStorage(int currentStorage) {
        this.currentStorage = currentStorage;
    }
}
