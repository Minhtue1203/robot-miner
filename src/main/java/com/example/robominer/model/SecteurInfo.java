package com.example.robominer.model;

import com.example.robominer.util.MineralType;
import com.example.robominer.util.SecteurType;

import java.util.ArrayList;
import java.util.List;

public class SecteurInfo {
    private SecteurType type;
    private int number;
    private int row;
    private int col;
    private MineralType mineralType;
    private int total;
    private int currentStock;

    public SecteurInfo(SecteurType type, int number, int row, int col, MineralType mineralType, int total) {
        this.type = type;
        this.number = number;
        this.row = row;
        this.col = col;
        this.mineralType = mineralType;
        this.total = total;
    }

    public SecteurInfo(SecteurType type, int number, int row, int col, MineralType mineralType, int currentStock, int total) {
        this.type = type;
        this.number = number;
        this.row = row;
        this.col = col;
        this.mineralType = mineralType;
        this.total = total;
        this.currentStock = currentStock;
    }

    @Override
    public String toString() {
        if (type.equals(SecteurType.WAREHOUSE)) {
            return String.format("%s %d at (%d, %d) %s %d", type, number, row, col, mineralType, total);
        }
        return String.format("%s %d at (%d, %d) with %s %d / %d", type, number, row, col, mineralType, currentStock, total);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public SecteurType getType() {
        return this.type;
    }

    public void setRow(int newRow) {
        this.row = newRow;
    }

    public void setCol(int newCol) {
        this.col = newCol;
    }
}
