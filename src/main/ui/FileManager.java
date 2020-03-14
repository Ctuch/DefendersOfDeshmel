package ui;

import com.google.gson.JsonSyntaxException;
import model.Board;
import model.Enemy;
import model.Person;
import model.SquareWallConfigs;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    private static final String GAME_SAVE_FILE = "./data/savedGame.txt";
    private Board board;
    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;

    public FileManager(Board board, ArrayList<Person> players, ArrayList<Enemy> enemies) {
        this.board = board;
        this.players = players;
        this.enemies = enemies;
    }

    //EFFECTS: saves the game to the GAME_SAVE_FILE if no exception thrown
    public void saveGame() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.write(board, players, enemies, SquareWallConfigs.getWallSetNum());
        } catch (IOException e) {
            System.out.println("uh oh, your game could not be saved.");
        }
    }

    //EFFECTS: clears the current save from GAME_SAVE_FILE if no exception thrown
    public void clearSave() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.clearSave();
        } catch (IOException e) {
            System.out.println("Something went wrong cleaning up old data. Keep on playing");
        }
    }

    //MODIFIES: this
    //EFFECTS: loads game from the GAME_SAVE_FILE. returns true if the game is loaded, false if the user needs to start
    //         a new one
    public boolean loadGame() {
        Reader reader = new Reader();
        try {
            reader.readFile(new File(GAME_SAVE_FILE));
            board.setBoard(reader.getBoardState());
            enemies = reader.getEnemies();
            players = reader.getPlayers();
            int wallConfigNum = reader.getWallConfigNumber();
            board.setWallConfig(SquareWallConfigs.generateRandomWallSet(wallConfigNum));
            System.out.println("Game Loaded");
            return true;
        } catch (IOException e) {
            System.out.println("I'm sorry, your game cannot be loaded. Please start a new game");
            return false;
        } catch (NullPointerException | JsonSyntaxException e) {
            System.out.println("There is no game to load. Please start a new game");
            return false;
        }
    }
}
