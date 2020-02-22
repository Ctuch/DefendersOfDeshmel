package persistence;

import com.google.gson.Gson;
import model.Board;
import model.Enemy;
import model.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

public class Reader {

    private BufferedReader bufferedReader;
    private ArrayList<Person> boardState;
    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;

    public Reader() {
        boardState = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public void readFile(File file) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(file));
        Gson gson = new Gson();

        Person[] boardStateArray = gson.fromJson(bufferedReader.readLine(), Person[].class);
        boardState.addAll(Arrays.asList(boardStateArray));

        Enemy[] enemyArray = gson.fromJson(bufferedReader.readLine(), Enemy[].class);
        enemies.addAll(Arrays.asList(enemyArray));

        Person[] playerArray = gson.fromJson(bufferedReader.readLine(), Person[].class);
        players.addAll(Arrays.asList(playerArray));
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

}
