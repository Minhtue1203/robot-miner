package com.example.robominer.model;

import com.example.robominer.util.TypeOre;

public class Robot extends Secteur {
    private int number;
    private TypeOre typeOre;
    private int capacityStorage;
    private int capacityExtraction;
    private int stockActuel;

//    public Robot(int number, TypeOre typeOre, int capacityStorage, int capacityExtraction) {
//        this.number = number;
//        this.typeOre = typeOre;
//        this.capacityStorage = capacityStorage;
//        this.capacityExtraction = capacityExtraction;
//        this.stockActuel = 0;

    public Robot(int number) {
        matrice[1][0] = 'R';
        matrice[1][1] = (char) ('0' + number);
    }

    public void move(String direction) {
        // Logique pour avancer
    }

    public void harvest() {
        // Logique pour récolter
    }

    public void deposit() {
        // Logique pour déposer
    }
}
