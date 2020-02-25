package persistence;

import com.google.gson.Gson;
import model.Enemy;
import model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// A reader that reads JSON objects in from a game save text file
public class Reader {

    private BufferedReader bufferedReader;
    private ArrayList<Person> boardState;
    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;
    private int wallConfigNumber;

    //EFFECTS: initializes an List each for storing the boardstate, players, and enemies
    public Reader() {
        boardState = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    //REQUIRES: file must be 4 lines long, in given order: boardstate, enemies, players, wallConfigNumber
    //MODIFIES: this
    //EFFECTS: reads in the above mentioned members from file, and points references to identical objects
    //         to the same object
    //         throws IOException if exception is raised reading from file
    public void readFile(File file) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(file));
        Gson gson = new Gson();

        Person[] boardStateArray = gson.fromJson(bufferedReader.readLine(), Person[].class);
        boardState.addAll(Arrays.asList(boardStateArray));

        Enemy[] enemyArray = gson.fromJson(bufferedReader.readLine(), Enemy[].class);
        enemies.addAll(Arrays.asList(enemyArray));

        Person[] playerArray = gson.fromJson(bufferedReader.readLine(), Person[].class);
        players.addAll(Arrays.asList(playerArray));

        wallConfigNumber = gson.fromJson(bufferedReader.readLine(), Integer.TYPE);
        combineObjects();
    }

    //MODIFIES: this
    //EFFECTS: cross references the enemies and boardstate lists to points references to identical objects to the
    //         same object
    //         cross references the players and boardstate lists and points references to identical objects to the same
    //         object
    private void combineObjects() {
        for (int i = 0; i < enemies.size(); i++) {
            int index = boardState.indexOf(enemies.get(i));
            if (index >= 0) {
                Person personEnemy = boardState.get(index);
                Enemy enemy = new Enemy(personEnemy.getName());
                enemy.setAttackPower(personEnemy.getAttackPower());
                enemy.setAvailable(false);
                enemy.takeDamage(enemy.getHealth() - personEnemy.getHealth());
                boardState.set(index, enemy);
                enemies.set(i, enemy);
            }
        }

        for (int i = 0; i < players.size(); i++) {
            int index = boardState.indexOf(players.get(i));
            if (index >= 0) {
                players.set(i, boardState.get(index));
            }
        }
    }

    public ArrayList<Person> getBoardState() {
        return boardState;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Person> getPlayers() {
        return players;
    }

    public int getWallConfigNumber() {
        return wallConfigNumber;
    }
}
