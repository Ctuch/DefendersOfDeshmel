package ui;

import com.google.gson.JsonSyntaxException;
import model.Board;
import model.Enemy;
import model.Person;
import model.SquareWallConfigs;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

//TODO: add different feedback than SOP! (display panel?)
//manages ui interaction with the files through the Reader and Writer classes
public class FileManager {

    private static final String GAME_SAVE_FILE = "./data/savedGame.txt";
    private Board board;
    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;
    private JLabel displayLabel;

    //EFFECTS: gets a copy of the board, players and enemies
    public FileManager(Board board, ArrayList<Person> players, ArrayList<Enemy> enemies, JLabel displayLabel) {
        this.board = board;
        this.players = players;
        this.enemies = enemies;
        this.displayLabel = displayLabel;
    }

    //EFFECTS: saves the game to the GAME_SAVE_FILE if no exception thrown
    public void saveGame() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.write(board, players, enemies, SquareWallConfigs.getWallSetNum());
        } catch (IOException e) {
            displayLabel.setText("uh oh, your game could not be saved.");
        }
    }

    //EFFECTS: clears the current save from GAME_SAVE_FILE if no exception thrown
    public void clearSave() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.clearSave();
        } catch (IOException e) {
            displayLabel.setText("Something went wrong cleaning up old data. Keep on playing");
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
            enemies.addAll(reader.getEnemies());
            players.addAll(reader.getPlayers());
            int wallConfigNum = reader.getWallConfigNumber();
            board.setWallConfig(SquareWallConfigs.generateRandomWallSet(wallConfigNum));
            displayLabel.setText("Game Loaded");
            return true;
        } catch (IOException e) {
            displayLabel.setText("I'm sorry, your game cannot be loaded. Please start a new game");
            return false;
        } catch (NullPointerException | JsonSyntaxException e) {
            displayLabel.setText("There is no game to load. Please start a new game");
            return false;
        }
    }

    //TODO: move this into reader class, (keep method and call it from here)
    //EFFECTS: returns true if there is a game to load, false otherwise or if an exception is thrown
    public boolean checkIfGameToLoad() {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(new File(GAME_SAVE_FILE)));
            String firstLine = fileReader.readLine();
            if (firstLine.equalsIgnoreCase("No save")) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
