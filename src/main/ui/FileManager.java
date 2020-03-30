package ui;

import com.google.gson.JsonSyntaxException;
import model.SquareWallConfigs;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.io.*;

import static model.GameComponents.*;

//manages ui interaction with the files through the Reader and Writer classes
public class FileManager {

    private static final String GAME_SAVE_FILE = "./data/savedGame.txt";
    private JLabel displayLabel;
    private Reader reader;

    //EFFECTS: gets a copy of the board, players and enemies
    public FileManager(JLabel displayLabel) {
        this.displayLabel = displayLabel;
        reader = new Reader();
    }

    //EFFECTS: saves the game to the GAME_SAVE_FILE if no exception thrown
    public void saveGame() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.write(getGameBoard(), getPlayers(), getEnemies(), SquareWallConfigs.getWallSetNum());
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
        try {
            reader.readFile(new File(GAME_SAVE_FILE));
            getGameBoard().setBoard(reader.getBoardState());
            getEnemies().addAll(reader.getEnemies());
            getPlayers().addAll(reader.getPlayers());
            int wallConfigNum = reader.getWallConfigNumber();
            getGameBoard().setWallConfig(SquareWallConfigs.generateRandomWallSet(wallConfigNum));
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

    //EFFECTS: returns true if there is a game to load, false otherwise or if an exception is thrown
    public boolean checkIfGameToLoad() {
        try {
            return reader.checkIfGameToLoad(new File(GAME_SAVE_FILE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
