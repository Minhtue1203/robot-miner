package com.example.robominer.util;
import java.util.Random;

public class Helper {
    public static Random random = new Random();

    public static int generateCapacityStorage() {
        return 5 + random.nextInt(11);
    }

    public static int generateCapacityExtraction() {
        return 1 + random.nextInt(4);
    }

    public static int generateMineQuantity() {
        return 50 + random.nextInt(51);
    }

    public static MineralType generateMineType(int count) {
        if (count % 2 == 0) {
            return MineralType.NICKEL;
        } else {
            return MineralType.GOLD;
        }
    }
}
