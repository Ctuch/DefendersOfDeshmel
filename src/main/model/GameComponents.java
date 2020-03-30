package model;

import java.util.ArrayList;

public class GameComponents {

    private static ArrayList<Person> players;
    private static ArrayList<Enemy> enemies;
    private static Board board;

    public GameComponents() {
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        board = new Board();
    }

    public static ArrayList<Person> getPlayers() {
        return players;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static Board getGameBoard() {
        return board;
    }

}
