package com.example.robominer.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @RepeatedTest(100) // Run the test 100 times to ensure reliability
    void testGenerateMineQuantity() {
        int randomNumber = Helper.generateMineQuantity();
        assertTrue(randomNumber >= 50 && randomNumber <= 100, "Random number should be between 50 and 100");
    }


    @RepeatedTest(10) // Run the test 10 times to ensure reliability
    void testGenerateCapacityExtraction() {
        int randomNumber = Helper.generateCapacityExtraction();
        assertTrue(randomNumber >= 1 && randomNumber <= 3, "Random number should be between 1 and 3");
    }

    @RepeatedTest(10) // Run the test 10 times to ensure reliability
    void testGenerateCapacityStorage() {
        int randomNumber = Helper.generateCapacityStorage();
        assertTrue(randomNumber >= 5 && randomNumber <= 10, "Random number should be between 5 and 10");
    }

    @Test
    void testGenerateTypeOdd() {
        assertEquals(MineralType.GOLD, Helper.generateMineType(1), "Number 1 should be classified as GOLD");
        assertEquals(MineralType.GOLD, Helper.generateMineType(3), "Number 3 should be classified as GOLD");
    }

    @Test
    void testGenerateTypeEven() {
        assertEquals(MineralType.NICKEL, Helper.generateMineType(0), "Number 0 should be classified as NICKEL");
        assertEquals(MineralType.NICKEL, Helper.generateMineType(2), "Number 2 should be classified as NICKEL");
        assertEquals(MineralType.NICKEL, Helper.generateMineType(4), "Number 4 should be classified as NICKEL");
    }
}