package com.example.robominer.model;

import com.example.robominer.util.MineralType;
import javafx.scene.paint.Color;

public class Robot extends Secteur {
    private int number;
    private MineralType mineralType;
    private int capacityStorage;
    private int capacityExtraction;
    private int currentStorage;
    private Color color;

    public Robot(int number, MineralType mineralType, int capacityStorage, int capacityExtraction) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + number);
        this.capacityStorage = capacityStorage;
        this.capacityExtraction = capacityExtraction;
        this.currentStorage = 0;
        this.mineralType = mineralType;
        this.number = number;
        this.color = generateColor(number);
    }

    private Color generateColor(int number) {
        Color[] colors = {
                Color.rgb(107, 62, 38), Color.rgb(255, 197, 217), Color.rgb(194, 242, 208),
                Color.rgb(253, 245, 201), Color.rgb(255, 203, 133), Color.rgb(0, 174, 219),
                Color.rgb(162, 0, 255), Color.rgb(244, 120, 53), Color.rgb(212, 18, 67), Color.rgb(142, 193, 39)
        };
        return colors[number % colors.length];
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
