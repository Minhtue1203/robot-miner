package com.example.robominer.model;

import com.example.robominer.util.MineralType;
import javafx.scene.paint.Color;

public class Mine extends Secteur {
    private MineralType mineralType;
    private int quantity;
    private int total;
    private int number;
    private Color color;

    public Mine(int number, MineralType mineralType, int quantity) {
        matrice[0][0] = 'M';
        matrice[0][1] = (char) ('0' + number);
        this.mineralType = mineralType;
        this.color = getColorMineralType(mineralType);
        this.quantity = quantity;
        this.total = quantity;
        this.number = number;
    }

    public Color getColorMineralType(MineralType mineralType) {
        if(mineralType.equals(MineralType.GOLD)) {
            return Color.YELLOW;
        } else {
            return Color.GRAY;
        }
    }

    public Color getColor() {
        return color;
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public int getTotal() {
        return total;
    }

    public MineralType getType() {
        return mineralType;
    }

    public void addRobot(Robot robot) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + robot.getNumber());
    }

    public void removeRobot() {
        System.out.println("remove");
        matrice[0][0] = 'M';
        matrice[0][1] = (char) ('0' + number);
        matrice[1][0] = '.';
        matrice[1][1] = '.';
    }

    public int getNumber() {
        return this.number;
    }

    public boolean isEmpty () {
        return this.quantity <= 0;
    }
}
