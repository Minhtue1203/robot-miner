package com.example.robominer.model;

import com.example.robominer.util.MineralType;
import javafx.scene.paint.Color;

import java.util.Random;

public class Robot extends Secteur {
    private int number;
    private MineralType mineralType;
    private int capacityStorage;
    private int capacityExtraction;
    private int currentStorage;
    private Color color;
    private Random rand = new Random();

    public Robot(int number, MineralType mineralType, int capacityStorage, int capacityExtraction) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + number);
        this.capacityStorage = capacityStorage;
        this.capacityExtraction = capacityExtraction;
        this.currentStorage = 0;
        this.mineralType = mineralType;
        this.number = number;
        this.color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public void addStorage(int amount) {
        this.currentStorage += amount;
    }

    public boolean hasStorageSpace() {
        return this.currentStorage < this.capacityStorage;
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

    public Color getColor() {
        return color;
    }

    public int getCurrentStorage() {
        return currentStorage;
    }

    public void setCurrentStorage(int currentStorage) {
        this.currentStorage = currentStorage;
    }
}
