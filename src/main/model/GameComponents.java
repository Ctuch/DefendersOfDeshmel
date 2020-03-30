package model;

import java.util.ArrayList;

//represents the members of a game, the board, and the lists of characters not in play
public class GameComponents {

    private static ArrayList<Person> players;
    private static ArrayList<Enemy> enemies;
    private static Board board;

    //EFFECTS: initializes an empty board and empty character lists
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
