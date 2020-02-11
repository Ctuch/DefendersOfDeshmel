package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {

    Weapon weapon;

    @BeforeEach
    public void runBefore() {
        weapon = new Weapon("Falchion", 3);
    }

    @Test
    public void testGetRange() {
        assertEquals(weapon.getRange(), 3);
    }

    @Test
    public void testGetName() {
        assertEquals(weapon.getName(), "Falchion");
    }

    @Test
    public void testToString() {
        assertEquals(weapon.toString(), "Name: Falchion, Range: 3");
    }
}
