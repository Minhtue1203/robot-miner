package com.example.robominer.util;
import com.example.robominer.model.Mine;

import java.awt.*;
import java.util.Random;
import javafx.scene.paint.Color;

public class Helper {
    public static Random random = new Random();

    public static int generateCapacityStorage() {
        return 5 + random.nextInt(5);
    }

    public static int generateCapacityExtraction() {
        return 1 + random.nextInt(3);
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

    public static Color getColorMine(Mine mine) {
        if(mine.getMineralType().equals(MineralType.GOLD)) {
            return Color.YELLOW;
        } else {
            return Color.GRAY;
        }
    }
}
