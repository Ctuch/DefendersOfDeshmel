package ui;

import model.Board;
import model.Person;

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
            addCharacter();
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
    // EFFECTS: initializes board and characters
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

    private void displayBoard() {
        ArrayList<Person> boardState = board.getBoard();

        String topBottomBorder = " ";
        String noTextRow = "|";
        for (int i = 0; i < 5; i++) {
            topBottomBorder += "------ ";
            noTextRow += "      |";
        }
        System.out.println(topBottomBorder);
        for (int i = 0; i < 5; i++) {
            String textRow = "|";
            for (int j = 0; j < 5; j++) {
                if (boardState.get(5 * i + j) != null) {
                    textRow += "  " + boardState.get(5 * i + j).getCharacterCode() + "  |";
                } else {
                    textRow += "      |";
                }
            }
            System.out.println(textRow);
            System.out.println(noTextRow);
            System.out.println(topBottomBorder);
        }
    }


    private Person selectCharacterToDisplay() {
        String command;

        System.out.println("Enter the character code you wish to learn about: ");
        command = input.next();
        command = command.toUpperCase();

        Person person = board.findPersonByCharacterCode(command);
        if (person != null) {
            return person;
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacterCode().equals(command)) {
                return players.get(i);
            }
        }
        return null;
    }

    private void displayCharacterStats(Person person) {
        if (person == null) {
            System.out.println("I'm sorry no character with that code exists");
        } else {
            System.out.println(person.toString());
        }
    }

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
        }
    }

    private void checkGameOver() {
        if (enemies.isEmpty()) {
            System.out.println("Congratulations you have vanquished the enemy!");
            gameOver = true;
        } else if (players.isEmpty()) {
            System.out.println("Oh no you have been destroyed.");
            gameOver = true;
        }
    }

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

    private void addCharacter() {
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
            String command;
            while (true) {
                System.out.println("Enter the character code you wish to add to the board: ");
                command = input.next();
                command = command.toUpperCase();

                Person person = null;
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCharacterCode().equals(command)) {
                        person = players.get(i);
                    }
                }
                if (person == null) {
                    System.out.println("Please enter an available person code");
                } else {
                    System.out.println("Enter the number of the square you want to add to (1-25 by row): ");
                    command = input.next();
                    int squareNum = Integer.parseInt(command);
                    boolean available = board.addCharacter(squareNum - 1, person);
                    if (available) {
                        break;
                    }
                    System.out.println("The square you want to add to is full, please try again");
                }
            }

        }
    }


}
