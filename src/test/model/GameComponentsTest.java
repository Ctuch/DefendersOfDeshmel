package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameComponentsTest {

    GameComponents game;

    @BeforeEach
    public void runBefore() {
        game = new GameComponents();
    }

    @Test
    public void testConstructPlayers() {
        ArrayList<Person> players = GameComponents.getPlayers();
        assertTrue(players.isEmpty());
        players.add(new Person("Ice Sorcerer"));
        assertEquals(players.get(0), new Person("Ice Sorcerer"));
    }

    @Test
    public void testConstructEnemies() {
        ArrayList<Enemy> enemies = GameComponents.getEnemies();
        assertTrue(enemies.isEmpty());
        enemies.add(new Enemy("Foot Soldier"));
        assertEquals(enemies.get(0), new Enemy("Foot Soldier"));
    }

    @Test
    public void testConstructBoard() {
        Board board = GameComponents.getGameBoard();
        for (Person person : board.getBoard()) {
            assertNull(person);
        }
    }
}
