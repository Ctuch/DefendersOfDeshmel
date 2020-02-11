package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Person ice;
    Person fire;
    Person soldier;

    @BeforeEach
    public void runBefore() {
        ice = new Person("Ice Sorcerer");
        fire = new Person("Fire Sorceress");
        soldier = new Person("Foot Soldier");
    }

    @Test
    public void testGainHealth() {
        int curHealth = ice.getHealth();
        ice.gainHealth(3);
        assertEquals(ice.getHealth(), curHealth + 3);
    }

    @Test
    public void testLoseHealth() {
        int curHealth = fire.getHealth();
        fire.takeDamage(7);
        assertEquals(fire.getHealth(), curHealth - 7);
    }

    @Test
    public void testIsDeadExact() {
        fire.takeDamage(7);
        assertFalse(fire.isDead());
        fire.takeDamage(3);
        assertTrue(fire.isDead());
    }

    @Test
    public void testIsDeadMore() {
        soldier.takeDamage(1);
        assertFalse(soldier.isDead());
        soldier.takeDamage(22);
        assertTrue(soldier.isDead());
    }

    @Test
    public void testGetWeapon() {
        Weapon greatSword = soldier.getWeapon();
        assertEquals(greatSword.getRange(), 1);
        assertEquals(greatSword.getName(), "Great Sword");
    }

    @Test
    public void testGetName() {
        assertEquals(fire.getName(), "Fire Sorceress");
    }

    @Test
    public void testAvailability() {
        assertTrue(ice.isAvailable());
        ice.setAvailable(false);
        assertFalse(ice.isAvailable());
    }

    @Test
    public void testGetAttacKPower() {
        assertEquals(fire.getAttackPower(), 3);
    }

    @Test
    public void testGetCharacterCode() {
        assertEquals(ice.getCharacterCode(), "IC");
    }

    @Test
    public void testToStringDead() {
        fire.takeDamage(30);
        assertEquals(fire.toString(), "Name: Fire Sorceress\nWeapon: " + fire.getWeapon().toString()
                + "\nAttack Power: 3\nHealth Status: Dead");
    }

    @Test
    public void testToStringNotDeadAvailable() {
        fire.takeDamage(3);
        assertEquals(fire.toString(), "Name: Fire Sorceress\nWeapon: " + fire.getWeapon().toString()
                + "\nAttack Power: 3\nHealth Status: 7 remaining health\nIn Play: false");
    }

    @Test
    public void testToStringNotDeadANotvailable() {
        fire.takeDamage(4);
        fire.setAvailable(false);
        assertEquals(fire.toString(), "Name: Fire Sorceress\nWeapon: " + fire.getWeapon().toString()
                + "\nAttack Power: 3\nHealth Status: 6 remaining health\nIn Play: true");
    }
}
