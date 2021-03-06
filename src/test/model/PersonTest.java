package model;

import javafx.print.PageLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Sound;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Person ice;
    Person ice2;
    Person fire;
    Enemy soldier;
    Enemy archer;
    Enemy sharpArch;
    Enemy knight;
    Board board;
    ArrayList<Person> players;


    @BeforeEach
    public void runBefore() {
        ice = new Person("Ice Sorcerer");
        ice2 = new Person("Ice Sorcerer");
        fire = new Person("Fire Sorceress");
        soldier = new Enemy("Foot Soldier");
        archer = new Enemy("Ranged Shooter");
        sharpArch = new Enemy("Sharp Shooter");
        knight = new Enemy("Warped Knight");
        board = new Board();
        players = new ArrayList<>();

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
                + "\nAttack Power: 3\nHealth Status: Dead\nSpecial Action: Has 2 charges remaining. "
                + "See the help menu for ability.");
    }

    @Test
    public void testToStringNotDeadAvailableNoCharges() {
        fire.takeDamage(3);
        fire.setNumSpecialActionCharges(0);
        assertEquals(fire.toString(), "Name: Fire Sorceress\nWeapon: " + fire.getWeapon().toString()
                + "\nAttack Power: 3\nHealth Status: 7 remaining health\nIn Play: false" +
                "\nSpecial Action: Has 0 charges remaining. See the help menu for ability.");
    }

    @Test
    public void testToStringNotDeadNotAvailable() {
        soldier.takeDamage(4);
        soldier.setAvailable(false);
        assertEquals(soldier.toString(), "Name: Foot Soldier\nWeapon: " + soldier.getWeapon().toString()
                + "\nAttack Power: 6\nHealth Status: 8 remaining health\nIn Play: true");
    }

    @Test
    public void testToStringHtmlDead() {
        fire.takeDamage(30);
        assertEquals(fire.toStringHtml(), "<html>Name: Fire Sorceress<br/>Weapon: " + fire.getWeapon().toString()
                + "<br/>Attack Power: 3<br/>Health Status: Dead<br/>Special Action: Has 2 charges remaining. "
                + "See the help menu for ability.</html>");
    }

    @Test
    public void testToStringHtmlNotDeadAvailableNoCharges() {
        fire.takeDamage(3);
        fire.setNumSpecialActionCharges(0);
        assertEquals(fire.toStringHtml(), "<html>Name: Fire Sorceress<br/>Weapon: " + fire.getWeapon().toString()
                + "<br/>Attack Power: 3<br/>Health Status: 7 remaining health<br/>In Play: false" +
                "<br/>Special Action: Has 0 charges remaining. See the help menu for ability.</html>");
    }

    @Test
    public void testToStringHtmlNotDeadNotAvailable() {
        soldier.takeDamage(4);
        soldier.setAvailable(false);
        assertEquals(soldier.toStringHtml(), "<html>Name: Foot Soldier<br/>Weapon: " + soldier.getWeapon().toString()
                + "<br/>Attack Power: 6<br/>Health Status: 8 remaining health<br/>In Play: true</html>");
    }

    @Test
    public void testIsEnemy() {
        assertTrue(soldier.isEnemy());
        assertFalse(fire.isEnemy());
    }

    @Test
    public void testSetAttackPower() {
        fire.setAttackPower(4);
        assertEquals(4, fire.getAttackPower());
    }

    @Test
    public void testHasChargesRemaining() {
        assertTrue(fire.hasChargesRemaining());
        fire.setNumSpecialActionCharges(0);
        assertFalse(fire.hasChargesRemaining());
        assertFalse(soldier.hasChargesRemaining());
    }

    @Test
    public void testSpecialActionIce() {
        board.addCharacter(12, soldier);
        board.addCharacter(2, fire);
        int soldierAP = soldier.getAttackPower();

        board.addCharacter(3, knight);
        int knightAP = knight.getAttackPower();

        ice.specialAction(board);
        assertTrue(ice.hasChargesRemaining());
        assertEquals(soldierAP - 1, soldier.getAttackPower());
        assertEquals(knightAP - 1, knight.getAttackPower());

        ice.specialAction(board);
        assertFalse(ice.hasChargesRemaining());
        assertEquals(soldierAP - 2, soldier.getAttackPower());
        assertEquals(1, knight.getAttackPower());
    }

    @Test
    public void testSpecialActionFire() {
        board.addCharacter(12, soldier);
        board.addCharacter(5, ice);
        int soldierHealth = soldier.getHealth();

        board.addCharacter(3, archer);
        int archerHealth = archer.getHealth();

        fire.specialAction(board);
        assertTrue(fire.hasChargesRemaining());
        assertEquals(soldierHealth - 3, soldier.getHealth());
        assertEquals(archerHealth - 3, archer.getHealth());

        fire.specialAction(board);
        assertFalse(fire.hasChargesRemaining());
        assertEquals(soldierHealth - 6, soldier.getHealth());
        assertEquals(archerHealth - 6, archer.getHealth());
    }

    @Test
    public void testSpecialActionEnemy() {
        board.addCharacter(4, soldier);
        board.addCharacter(7, ice);
        int soldierHealth = soldier.getHealth();

        board.addCharacter(21, archer);
        int archerHealth = archer.getHealth();

        assertFalse(soldier.hasChargesRemaining());
        soldier.specialAction(board);
        assertFalse(soldier.hasChargesRemaining());
        assertEquals(soldierHealth, soldier.getHealth());
        assertEquals(archerHealth, archer.getHealth());
    }

    @Test
    public void testSpecialActionString() {
        assertEquals("Damage all enemies by 3", fire.getSpecialActionString());
        assertEquals("Decrease all enemy attack power by 1", ice.getSpecialActionString());
        assertEquals("", soldier.getSpecialActionString());
    }

    @Test
    public void testEquals() {
        Person fire2 = new Person("Fire Sorceress");
        assertEquals(ice, ice2);
        assertEquals(fire, fire2);
        assertEquals(ice, ice);
        assertNotEquals(ice, board);
        assertNotEquals(ice, fire);
    }

    @Test
    public void testEqualsDifferentAttackPower() {
        ice.setAttackPower(10);
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentHealth() {
        ice.gainHealth(15);
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentAvailability() {
        ice.setAvailable(false);
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentEnemyStatus() {
        ice.isEnemy = true;
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentNumSpecialCharges() {
        ice.setNumSpecialActionCharges(30);
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentName() {
        ice.setName("Ice Soldier");
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentNumWeapon() {
        ice.setWeapon(new Weapon("Bayonet", 1));
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentCharacterCode() {
        ice.setCharacterCode("ID");
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testEqualsDifferentSpecialActionString() {
        ice.setSpecialActionString("Implodes killing all enemies on adjacent squares");
        assertNotEquals(ice, ice2);
    }

    @Test
    public void testHashCode() {
        Person ice2 = new Person("Ice Sorcerer");
        assertEquals(ice2.hashCode(), ice.hashCode());
        assertNotEquals(ice.hashCode(), fire.hashCode());
    }

    @Test
    public void testAddEnemies() {
        Person soldierDecoy = new Enemy("Foot Soldier");
        players.add(soldierDecoy);
        Person.addPlayers(players);
        assertEquals(fire, players.get(0));
        assertEquals(ice, players.get(1));
        assertEquals(players.size(), 2);
        assertFalse(players.contains(soldierDecoy));

        Person.addPlayers(players);
        assertEquals(fire, players.get(0));
        assertEquals(ice, players.get(1));
        assertEquals(players.size(), 2);
    }

    @Test
    public void testWallLocation() {
        ice.setLocation(933, 47);
        assertEquals(933, ice.getLocationX());
        assertEquals(47, ice.getLocationY());
    }

    @Test
    public void testGetAttackSound() {
        assertEquals(knight.getAttackSound(), Sound.SWORD_FIGHT);
        assertEquals(sharpArch.getAttackSound(), Sound.ARCHER);
    }
}