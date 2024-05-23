package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Warehouse extends Secteur {
    private MineralType mineralType;
    private int stock;

    public Warehouse(int warehouseNumber, MineralType mineralType) {
        matrice[0][0] = 'E';
        matrice[0][1] = (char) ('0' + warehouseNumber);
        this.mineralType = mineralType;
        this.stock = 0;
    }

    public void store(int quantityMineral) {
        this.stock += quantityMineral;
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public int getStock() {
        return stock;
    }
}
