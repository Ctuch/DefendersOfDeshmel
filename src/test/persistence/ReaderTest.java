package persistence;

import model.Enemy;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    Reader reader;
    Person footSoldierExpected;
    Person iceSorcererExpected;
    Person rangedShooterExpected;
    Person fireSorceressExpected;


    @BeforeEach
    public void runBefore() {
        reader = new Reader();
        footSoldierExpected = new Enemy("Foot Soldier");
        iceSorcererExpected = new Person("Ice Sorcerer");
        rangedShooterExpected = new Enemy("Ranged Shooter");
        fireSorceressExpected = new Person("Fire Sorceress");
    }

    @Test
    public void testThrowIOException() {
        try {
            reader.readFile(new File("./data/notExistentFile.txt"));
            fail();
        } catch (IOException e) {
            //good
        }
    }

    @Test
    public void testReadGameSave1() {

        footSoldierExpected.setAvailable(false);
        footSoldierExpected.setAttackPower(5);

        iceSorcererExpected.takeDamage(4);
        iceSorcererExpected.setAvailable(false);
        iceSorcererExpected.setNumSpecialActionCharges(1);

        rangedShooterExpected.setAvailable(false);
        rangedShooterExpected.setAttackPower(3);
        rangedShooterExpected.takeDamage(8);

        fireSorceressExpected.setAvailable(false);
        fireSorceressExpected.setNumSpecialActionCharges(1);

        try {
            reader.readFile(new File("./data/testGameSave1.txt"));

            ArrayList<Person> boardState = reader.getBoardState();
            Person footSoldier = boardState.get(2);
            assertEquals(footSoldierExpected, footSoldier);

            Person iceSorcerer = boardState.get(12);
            assertEquals(iceSorcererExpected, iceSorcerer);

            Person rangedShooter = boardState.get(14);
            assertEquals(rangedShooterExpected, rangedShooter);

            Person fireSorceress = boardState.get(20);
            assertEquals(fireSorceressExpected, fireSorceress);

            ArrayList<Enemy> enemies = reader.getEnemies();
            assertEquals(footSoldierExpected, enemies.get(0));
            assertEquals(rangedShooterExpected, enemies.get(1));

            footSoldier.takeDamage(3);
            assertEquals(footSoldier, enemies.get(0));

            ArrayList<Person> players = reader.getPlayers();
            assertEquals(fireSorceressExpected, players.get(0));
            assertEquals(iceSorcererExpected, players.get(1));

            iceSorcerer.setNumSpecialActionCharges(0);
            assertEquals(iceSorcerer, players.get(1));

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testReadGameSave2() {
        rangedShooterExpected.takeDamage(3);
        rangedShooterExpected.setAvailable(false);

        try {
            reader.readFile(new File("./data/testGameSave2.txt"));

            ArrayList<Person> boardState = reader.getBoardState();
            Person rangedShooter = boardState.get(17);
            assertEquals(rangedShooterExpected, rangedShooter);

            ArrayList<Enemy> enemies = reader.getEnemies();
            assertEquals(rangedShooterExpected, enemies.get(0));
            assertEquals(footSoldierExpected, enemies.get(1));

            ArrayList<Person> players = reader.getPlayers();
            assertEquals(iceSorcererExpected, players.get(0));

        } catch (IOException e) {
            fail();
        }
    }
}
