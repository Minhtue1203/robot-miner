package com.example.robominer.model;

import com.example.robominer.util.Helper;
import com.example.robominer.util.MineralType;
import com.example.robominer.util.StatusRobotType;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Robot extends Secteur {
    private int number;
    private MineralType mineralType;
    private int capacityStorage;
    private int capacityExtraction;
    private int currentStorage;
    private Color color;
    private Random rand = new Random();
    private static ArrayList<Color> robotColors = new ArrayList<>();
    private int x;
    private int y;
    private StatusRobotType status;
    public Robot(int x, int y, int number, MineralType mineralType, int capacityStorage, int capacityExtraction) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + number);
        this.capacityStorage = capacityStorage;
        this.capacityExtraction = capacityExtraction;
        this.currentStorage = 0;
        this.mineralType = mineralType;
        this.number = number;
        initColors();
        this.color = robotColors.get(number);
        this.x = x;
        this.y = y;
        this.status = StatusRobotType.FINDING;
    }

    private void initColors () {
        // Add colors to the list. Max 10 robots
        robotColors.add(Color.rgb(107, 62, 38));
        robotColors.add(Color.rgb(255, 197, 217));
        robotColors.add(Color.rgb(194, 242, 208));
        robotColors.add(Color.rgb(253, 245, 201));
        robotColors.add(Color.rgb(255, 203, 133));
        robotColors.add(Color.rgb(0,174,219));
        robotColors.add(Color.rgb(162,0,255));
        robotColors.add(Color.rgb(244,120,53));
        robotColors.add(Color.rgb(212,18,67));
        robotColors.add(Color.rgb(142,193,39));
    }

    public void setStatus(StatusRobotType newState) {
        this.status = newState;
    }

    public StatusRobotType getStatus() {
        return this.status;
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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int [] getCurrentPosition() {
        return new int[]{ this.x, this.y };
    }

    public void setCurrentStorage(int currentStorage) {
        this.currentStorage = currentStorage;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMining() {
        return this.status == StatusRobotType.MINING;
    }

    public boolean isFinding() {
        return this.status == StatusRobotType.FINDING;
    }

    public boolean isDepositing() {
        return this.status == StatusRobotType.DEPOSITING;
    }
}
