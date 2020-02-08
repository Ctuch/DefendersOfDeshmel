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

            if (command.equals("q")) {
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

        ArrayList<Person> boardState = board.getBoard();
        for (int i = 0; i < 25; i++) {
            if (boardState.get(i) != null && boardState.get(i).getCharacterCode().equals(command)) {
                return boardState.get(i);
            }
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
    }

    private void moveCharacter() {
    }

    private void addCharacter() {
    }

}
