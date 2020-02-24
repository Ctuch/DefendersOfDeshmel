package persistence;

import model.Board;
import model.Enemy;
import model.Person;
import model.SquareWallConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {

    private static final String TEST_FILE = "./data/testGameSave.txt";

    private Writer writer;
    private Reader reader;
    private Board board;
    private ArrayList<Enemy> enemies;
    private ArrayList<Person> players;

    @BeforeEach
    public void runBefore() throws IOException {
        writer = new Writer(new File(TEST_FILE));
        reader = new Reader();

        enemies = new ArrayList<>();
        enemies.add(new Enemy("Foot Soldier"));
        enemies.add(new Enemy("Ranged Shooter"));

        players = new ArrayList<>();
        players.add(new Person("Ice Sorcerer"));
        players.add(new Person("Fire Sorceress"));

        board = new Board();
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(7, enemies.get(0));
        board.addCharacter(12, enemies.get(1));
        board.addCharacter(23, players.get(0));
        board.addCharacter(1, players.get(1));

    }

    @Test
    public void testWritePlayersAndBoard() {
        try {
            writer.write(board, players, enemies);

            reader.readFile(new File(TEST_FILE));
            ArrayList<Person> boardState = reader.getBoardState();
            ArrayList<Enemy> enemyList = reader.getEnemies();
            ArrayList<Person> playerList = reader.getPlayers();

            assertEquals(boardState.get(7), enemies.get(0));
            assertEquals(boardState.get(12), enemies.get(1));
            assertEquals(boardState.get(23), players.get(0));
            assertEquals(boardState.get(1), players.get(1));

            assertEquals(enemyList.get(0), enemies.get(0));
            assertEquals(enemyList.get(1), enemies.get(1));
            assertEquals(playerList.get(0), players.get(0));
            assertEquals(playerList.get(1), players.get(1));

        } catch (IOException e) {
            fail();
        }

    }
}
