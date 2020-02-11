package model;

import java.util.ArrayList;
import java.util.Random;

//represents a 5v5 grid with filled or empty squares, some separated by walls
//a filled square will display a 2 character code indicating what occupies that space
/*   0  1  2  3  4
     5  6  7  8  9
    10 11 12 13 14
    15 16 17 18 19
    20 21 22 23 24
 */

public class Board {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    //private static final ArrayList<SquareWall> WALL_OPTIONS = new ArrayList<>();


    private ArrayList<Person> board;
    private ArrayList<Integer> wallConfig;


    //EFFECTS: An empty board grid is generated and a wall configuration is created
    public Board() {
        board = new ArrayList<>(25);
        fillBoardWithNull();
        //wallConfig = generateWallConfig();

    }

    public ArrayList<Person> getBoard() {
        return board;
    }

    //MODIFIES: this
    //EFFECTS: fills every board index with a null value
    private void fillBoardWithNull() {
        for (int i = 0; i < 25; i++) {
            board.add(null);
        }
    }

    //EFFECTS: randomly selects on of the wall configs
    //         NOT IN USE CURRENTLY NO TESTS IMPLEMENTED
    /*private ArrayList<Integer> generateWallConfig() {
        Random rn = new Random();
        int selection = rn.nextInt(5) + 1;
        return null;
        //5 options, randomly select one. statically create them as finals above?
    }*/

    //REQUIRES: 0 <= squareNum <= 24
    //MODIFIES: this
    //EFFECTS: adds character of name (and associated attributes) to selected tile if available and returns true
    //         returns false if square is taken or character is already dead/in play
    public boolean addCharacter(int squareNum, Person person) {
        if (person.isAvailable() && board.get(squareNum) == null) {
            board.set(squareNum, person);
            person.setAvailable(false);
            return true;
        }
        return false;
    }

    //REQUIRES: person is not null, and already on board, 0 <= direction <= 3
    //MODIFIES: this
    //EFFECTS:  moves character one square in specified direction and returns true, or false if square is full or
    //          or off the board
    public boolean moveCharacter(int direction, Person person) {
        int currentPosition = board.indexOf(person);
        switch (direction) {
            case LEFT:
                return moveLeft(currentPosition, person);
            case RIGHT:
                return moveRight(currentPosition, person);
            case UP:
                return moveUp(currentPosition, person);
            case DOWN:
                return moveDown(currentPosition, person);
            default:
                return false;
        }
    }

    //REQUIRES: person is not null, and already on board, 0 <= direction <= 3
    //MODIFIES: this
    //EFFECTS:  moves character one square left and returns true, or false if square is full or
    //          or off the board
    private boolean moveLeft(int currentPosition, Person person) {
        if (currentPosition % 5 == 0 || board.get(currentPosition - 1) != null) {
            return false;
        }
        board.set(currentPosition, null);
        board.set(currentPosition - 1, person);
        return true;
    }

    //REQUIRES: person is not null, and already on board, 0 <= direction <= 3
    //MODIFIES: this
    //EFFECTS:  moves character one square right and returns true, or false if square is full or
    //          or off the board
    private boolean moveRight(int currentPosition, Person person) {
        if (currentPosition % 5 == 4 || board.get(currentPosition + 1) != null) {
            return false;
        }
        board.set(currentPosition, null);
        board.set(currentPosition + 1, person);
        return true;
    }

    //REQUIRES: person is not null, and already on board, 0 <= direction <= 3
    //MODIFIES: this
    //EFFECTS:  moves character one square up and returns true, or false if square is full or
    //          or off the board
    private boolean moveUp(int currentPosition, Person person) {
        if (currentPosition <= 4 || board.get(currentPosition - 5) != null) {
            return false;
        }
        board.set(currentPosition, null);
        board.set(currentPosition - 5, person);
        return true;
    }

    //REQUIRES: person is not null, and already on board, 0 <= direction <= 3
    //MODIFIES: this
    //EFFECTS:  moves character one square down and returns true, or false if square is full or
    //          or off the board
    private boolean moveDown(int currentPosition, Person person) {
        if (currentPosition >= 20 || board.get(currentPosition + 5) != null) {
            return false;
        }
        board.set(currentPosition, null);
        board.set(currentPosition + 5, person);
        return true;
    }

    //REQUIRES: person1 and person2 are not null and on board
    //EFFECTS: returns true if person2 is in weapon range of person1 (not diagonally for now)
    public boolean isInWeaponRange(Person person1, Person person2) {
        int weaponRangeP1 = person1.getWeapon().getRange();
        int positionP1 = board.indexOf(person1);
        int positionP2 = board.indexOf(person2);
        int rowNumP1 = positionP1 / 5;
        int colNumP1 = positionP1 % 5;
        int rowNumP2 = positionP2 / 5;
        int colNumP2 = positionP2 % 5;

        if (rowNumP1 == rowNumP2 && Math.abs(colNumP1 - colNumP2) <= weaponRangeP1) {
            return true;
        } else {
            return colNumP1 == colNumP2 && Math.abs(rowNumP1 - rowNumP2) <= weaponRangeP1;
        }
    }

    //EFFECTS: returns the Person object associated with the given character code
    public Person findPersonByCharacterCode(String code) {
        for (int i = 0; i < 25; i++) {
            if (board.get(i) != null && board.get(i).getCharacterCode().equalsIgnoreCase(code)) {
                return board.get(i);
            }
        }
        return null;
    }
}
