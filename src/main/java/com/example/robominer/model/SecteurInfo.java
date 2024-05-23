package com.example.robominer.model;

import com.example.robominer.util.MineralType;
import com.example.robominer.util.SecteurType;

public class SecteurInfo {
    private SecteurType type;
    private int number;
    private int row;
    private int col;
    private MineralType mineralType;
    private int quantity;

    public SecteurInfo(SecteurType type, int number, int row, int col) {
        this.type = type;
        this.number = number;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        if (type.equals(SecteurType.ROBOT)) {
            return String.format("%s %d at (%d, %d) with %d %s", type, number, row, col, quantity, mineralType);
        }
        return String.format("%s %d at (%d, %d)", type, number, row, col);
    }
}
