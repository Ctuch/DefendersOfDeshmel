package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareWallTest {

    SquareWall defaultWall;
    SquareWall customWall;

    @BeforeEach
    public void runBefore() {
        defaultWall = new SquareWall();
        customWall = new SquareWall(true, false, true,false);
    }

    @Test
    public void testIsLeftWall() {
        assertFalse(defaultWall.isLeftWall());
        assertTrue(customWall.isLeftWall());
    }

    @Test
    public void testIsRightWall() {
        assertFalse(defaultWall.isRightWall());
        assertFalse(customWall.isRightWall());
    }

    @Test
    public void testIsUpperWall() {
        assertFalse(defaultWall.isUpperWall());
        assertTrue(customWall.isUpperWall());
    }

    @Test
    public void testIsLowerWall() {
        assertFalse(defaultWall.isLowerWall());
        assertFalse(customWall.isLowerWall());
    }

    @Test
    public void testWallLocation() {
        defaultWall.setLocation(13, 977);
        assertEquals(13, defaultWall.getLocationX());
        assertEquals(977, defaultWall.getLocationY());
    }

}