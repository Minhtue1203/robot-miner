package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Mine extends Secteur {
    private MineralType mineralType;
    private int quantity;
    private int total;

    public Mine(int mineNumber, MineralType mineralType, int quantity) {
        matrice[0][0] = 'M';
        matrice[0][1] = (char) ('0' + mineNumber);
        this.mineralType = mineralType;
        this.quantity = quantity;
        this.total = quantity;
    }

    public void extract(int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            quantity = this.quantity;
            this.quantity = 0;
        }
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return total;
    }
}
