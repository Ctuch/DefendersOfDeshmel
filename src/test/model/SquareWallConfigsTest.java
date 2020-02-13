package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SquareWallConfigsTest {
    SquareWallConfigs sqCon;

    @BeforeEach
    public void runBefore() {
        sqCon = new SquareWallConfigs();
}
    @Test
    public void testGenerateWallSetOne() {
        ArrayList<SquareWall> wallOne = sqCon.generateRandomWallSet(1);

        SquareWall square19 = wallOne.get(19);
        assertFalse(square19.isLeftWall());
        assertTrue(square19.isRightWall());
        assertFalse(square19.isUpperWall());
        assertFalse(square19.isLowerWall());

        SquareWall square10 = wallOne.get(10);
        assertFalse(square10.isLeftWall());
        assertFalse(square10.isRightWall());
        assertFalse(square10.isUpperWall());
        assertFalse(square10.isLowerWall());
    }

    @Test
    public void generateWallSetDefault() {
        ArrayList<SquareWall> wallEmpty = sqCon.generateRandomWallSet(-1);
        for (int i = 0; i < 25; i++) {
            assertFalse(wallEmpty.get(i).isLowerWall());
            assertFalse(wallEmpty.get(i).isUpperWall());
            assertFalse(wallEmpty.get(i).isLeftWall());
            assertFalse(wallEmpty.get(i).isRightWall());
        }
    }

    @Test
    public void testGetWalls() {
        assertEquals(sqCon.getWalls().size(), 25);
    }
}
