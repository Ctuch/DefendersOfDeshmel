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

    @Test
    public void testEquals() {
        Weapon weapon2 = new Weapon("Falchion", 3);
        Weapon weapon3 = new Weapon("Falchion", 1);
        Weapon weapon4 = null;
        Weapon weapon5 = new Weapon("Glave", 3);
        assertEquals(weapon2, weapon);
        assertNotEquals(weapon, weapon3);
        assertEquals(weapon, weapon);
        assertNotEquals(weapon, weapon4);
        assertNotEquals(weapon, "String");
        assertNotEquals(weapon2, weapon5);

    }

    @Test
    public void testHashCode() {
        Weapon weapon2 = new Weapon("Falchion", 3);
        Weapon weapon3 = new Weapon("Falchion", 1);
        assertEquals(weapon2.hashCode(), weapon.hashCode());
        assertNotEquals(weapon.hashCode(), weapon3.hashCode());
        assertEquals(weapon.hashCode(), weapon.hashCode());
    }
}
