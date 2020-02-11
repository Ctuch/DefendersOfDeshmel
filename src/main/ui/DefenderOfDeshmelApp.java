package ui;

import model.Board;
import model.Person;
import model.SquareWall;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Defenders of Deshmel application - provides user interaction with board and characters
//Based off of TellerApp provided to CPSC 210. URL: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//Specifically references class TellerApp.java
public class DefenderOfDeshmelApp {

    private File rules;
    private Board board;
    private ArrayList<Person> enemies;
    private ArrayList<Person> players;
    private Scanner input;
    private BufferedReader br;
    private boolean gameOver;

    // EFFECTS: runs the teller application
    public DefenderOfDeshmelApp() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGame() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (gameOver || command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for playing!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            displayRemainingCharacters();
            displayBoard();
        } else if (command.equals("m")) {
            moveCharacter();
            displayBoard();
        } else if (command.equals("x")) {
            attackAction();
            checkGameOver();
            displayBoard();
        } else if (command.equals("h")) {
            displayHelp();
        } else if (command.equals("d")) {
            Person p = selectCharacterToDisplay();
            displayCharacterStats(p);
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes board and characters, reads in rules
    private void init() {
        gameOver = false;
        board = new Board();

        enemies = new ArrayList<>();
        // need to get a real list together of enemies and player characters, add here
        enemies.add(new Person("Foot Soldier"));
        enemies.add(new Person("Foot Soldier"));

        players = new ArrayList<>();
        players.add(new Person("Fire Sorceress"));
        players.add(new Person("Ice Sorcerer"));

        input = new Scanner(System.in);

        rules = new File("data/rules.txt");
        try {
            br = new BufferedReader(new FileReader(rules));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("I'm sorry I guess you'll have to play without rules");
        }

        displayBoard();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add character");
        System.out.println("\tm -> move character");
        System.out.println("\tx -> attack");
        System.out.println("\th -> help menu");
        System.out.println("\td -> display character stats");
        System.out.println("\tq -> quit");
    }

    //EFFECTS: displays current state of board to user
    private void displayBoard() {
        String bottomBorder = " ";

        for (int i = 0; i < 5; i++) {
            String topBorder = " ";
            String textRow = "";
            String noTextRow = "";
            SquareWall wall = null;

            for (int j = 0; j < 5; j++) {
                wall = board.getWallConfig().get(5 * i + j);
                Person person = board.getBoard().get(5 * i + j);

                topBorder += createUpperBorder(wall);

                if (i == 4) {
                    bottomBorder += createLowerBorder(wall);
                }
                String[] middleRows = createLeftBorder(wall, person);
                textRow += middleRows[0];
                noTextRow += middleRows[1];
            }
            String[] finalCharacters = createRightMostCharacter(wall);
            textRow += finalCharacters[0];
            noTextRow += finalCharacters[1];

            System.out.println(topBorder + "\n" + textRow + "\n" + noTextRow);
        }
        System.out.println(bottomBorder);
    }

    //EFFECTS: adds the final character of each row depending on whether there is a wall there or not
    private String[] createRightMostCharacter(SquareWall wall) {
        String textRow;
        String noTextRow;
        if (wall.isRightWall()) {
            textRow = "#";
            noTextRow = "#";
        } else {
            textRow = "|";
            noTextRow = "|";
        }
        return new String[] {textRow, noTextRow};
    }

    //EFFECTS: left side of each square and the character codes depending on whether there is a wall there or not
    //         and if the square is full
    private String[] createLeftBorder(SquareWall wall, Person person) {
        String textRow = "";
        String noTextRow = "";
        if (wall.isLeftWall()) {
            if (person != null) {
                textRow += "#  " + person.getCharacterCode() + "  ";
            } else {
                textRow += "#      ";
            }
            noTextRow += "#      ";
        } else {
            if (person != null) {
                textRow += "|  " + person.getCharacterCode() + "  ";
            } else {
                textRow += "|      ";
            }
            noTextRow += "|      ";
        }
        return new String[] {textRow, noTextRow};
    }

    //EFFECTS: adds the final border row depending on whether there is a wall there or not
    private String createLowerBorder(SquareWall wall) {
        if (wall.isLowerWall()) {
            return  "###### ";
        } else {
            return  "------ ";
        }
    }

    //EFFECTS: adds the border row above the square depending on whether there is a wall there or not
    private String createUpperBorder(SquareWall wall) {
        if (wall.isUpperWall()) {
            return  "###### ";
        } else {
            return  "------ ";
        }
    }

    //EFFECTS: prompts user to select a character code and produces the associated character or null if doesn't exist/
    //         is already dead
    private Person selectCharacterToDisplay() {
        String command;

        System.out.println("Enter the character code you wish to learn about: ");
        command = input.next();
        command = command.toUpperCase();

        Person person = board.findPersonByCharacterCode(command);
        if (person != null) {
            return person;
        }
        for (Person player : players) {
            if (player.getCharacterCode().equals(command)) {
                return player;
            }
        }
        return null;
    }

    //EFFECTS: displays character stats if person is not null
    private void displayCharacterStats(Person person) {
        if (person == null) {
            System.out.println("I'm sorry no alive character with that code exists");
        } else {
            System.out.println(person.toString());
        }
    }

    //EFFECTS: displays rules from the rules.txt file
    private void displayHelp() {
        String readingRules = "";
        while (true) {
            try {
                readingRules = br.readLine();
            } catch (IOException e) {
                System.out.println("Sorry, something went wrong trying to display the rules");
                e.printStackTrace();
            }
            if (readingRules == null) {
                break;
            }
            System.out.println(readingRules);
        }
    }

    //MODIFIES: board
    //EFFECTS: specifies and attacker and a defender, and has the defender take damage if in range
    private void attackAction() {
        String command;
        Person player;
        Person enemy;

        while (true) {
            System.out.println("Enter the codes for the character you wish to attack with + attack, comma separated: ");
            System.out.println("Example: AA,EE");
            command = input.next();
            command = command.toUpperCase();
            if (command.length() != 5) {
                System.out.println("Please enter 5 characters");
            } else {
                String playerCode = command.substring(0, 2);
                String enemyCode = command.substring(3);
                player = board.findPersonByCharacterCode(playerCode);
                enemy = board.findPersonByCharacterCode(enemyCode);
                if (player == null || enemy == null) {
                    System.out.println("Please make sure both characters selected are on the board.");
                } else {
                    break;
                }
            }
        }
        attack(player, enemy);
    }

    //MODIFIES: board, enemies or players if defender dies
    //EFFECTS: has defender take damage from attacker if in range, and removes defender from game if dead
    private void attack(Person attacker, Person defender) {
        if (board.isInWeaponRange(attacker, defender)) {
            defender.takeDamage(attacker.getAttackPower());
            System.out.println(defender.getName() + " has lost " + attacker.getAttackPower() + " health");
            if (defender.isDead()) {
                System.out.println(defender.getName() + " is dead");
                int square = board.getBoard().indexOf(defender);
                board.getBoard().set(square, null);
                if (enemies.contains(defender)) {
                    enemies.remove(defender);
                } else {
                    players.remove(defender);
                }
            }
        } else {
            System.out.println("You are out of range to make that attack");
        }
    }

    //MODIFIES: gameOver
    //EFFECTS: determines whether the game should end if there are no enemies or no players remaining
    //         displays phrase indicating winning team
    private void checkGameOver() {
        if (enemies.isEmpty()) {
            System.out.println("Congratulations you have vanquished the enemy!");
            gameOver = true;
        } else if (players.isEmpty()) {
            System.out.println("Oh no you have been destroyed.");
            gameOver = true;
        }
    }

    //MODIFIES: board
    //EFFECTS: prompts character for a user and direction, and moves that character if both are valid inputs
    private void moveCharacter() {
        String command;
        boolean successful = false;

        while (!successful) {
            System.out.println("Enter the character code for the player you wish to move: ");
            command = input.next();
            command = command.toUpperCase();

            Person person = board.findPersonByCharacterCode(command);
            if (person == null) {
                System.out.println("That character is not on the board. Please try again");
            } else {
                System.out.println("Enter the number of the direction to move, 0 = Left, 1 = Right, 2 = Up, 3 = Down:");
                command = input.next();
                int direction = Integer.parseInt(command);

                successful = board.moveCharacter(direction, person);
                if (!successful) {
                    System.out.println("That character is unable to move in that direction.");
                }
            }
        }
    }

    //EFFECTS: displays a list of remaining characters and prompts you to selects and place one if any are available
    private void displayRemainingCharacters() {
        boolean remainingCharacters = false;
        System.out.println("The following characters are available to place on the board: ");
        for (Person p : players) {
            if (p.isAvailable()) {
                System.out.println("(" + p.getCharacterCode() + ")" + p.getName());
                remainingCharacters = true;
            }
        }
        if (!remainingCharacters) {
            System.out.println("There are no characters remaining that can be placed on the board");
        } else {
            addCharacter();
        }
    }

    //MODIFIES: board
    //EFFECTS: places character onto board if available and square is available, otherwise prompts for new character
    //         or square until a proper one is given
    private void addCharacter() {
        String command;
        while (true) {
            System.out.println("Enter the character code you wish to add to the board: ");
            command = input.next();
            command = command.toUpperCase();

            Person person = null;
            for (Person player : players) {
                if (player.getCharacterCode().equals(command) && player.isAvailable()) {
                    person = player;
                }
            }
            if (person == null) {
                System.out.println("Please enter an available person code");
            } else {
                System.out.println("Enter the number of the square you want to add to (1-25 by row): ");
                command = input.next();
                int squareNum = Integer.parseInt(command);
                if (board.addCharacter(squareNum - 1, person)) {
                    break;
                }
                System.out.println("The square you want to add to is full, please try again");
            }
        }
    }
}
