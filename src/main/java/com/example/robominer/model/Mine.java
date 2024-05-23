package com.example.robominer.model;

import com.example.robominer.util.MineralType;

public class Mine extends Secteur {
    private MineralType mineralType;
    private int quantity;

//    public Mine(int mineNumber, TypeOre typeOre, int quantity) {
//        this.typeOre = typeOre;
//        this.quantity = quantity;
    public Mine(int mineNumber, MineralType mineralType, int quantity) {
        matrice[0][0] = 'M';
        matrice[0][1] = (char) ('0' + mineNumber);
        this.mineralType = mineralType;
        this.quantity = quantity;
    }

    public void extract(int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            quantity = this.quantity;
            this.quantity = 0;
        }
    }
}
