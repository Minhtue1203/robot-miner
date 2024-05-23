package com.example.robominer.model;

import com.example.robominer.util.TypeOre;

public class Warehouse extends Secteur {
    private TypeOre typeOre;
    private int stock;

    public Warehouse(int warehouseNumber) {
//        this.typeOre = typeOre;
        this.stock = 0;
        matrice[0][0] = 'E';
        matrice[0][1] = (char) ('0' + warehouseNumber);
    }

    public void store(int quantityOre) {
        this.stock += quantityOre;
    }
}
