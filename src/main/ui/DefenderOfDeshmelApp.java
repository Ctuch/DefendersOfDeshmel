package ui;

import com.google.gson.JsonSyntaxException;
import model.*;
import persistence.Reader;
import persistence.Writer;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Defenders of Deshmel application - provides user interaction with board and characters
//Based off of TellerApp provided to CPSC 210. URL: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//Specifically references class TellerApp.java
public class DefenderOfDeshmelApp {
    //TODO: enemy takes turn when trying to add a character when you have none left
    private static final String GAME_SAVE_FILE = "./data/savedGame.txt";
    private Board board;
    private ArrayList<Enemy> enemies;
    private ArrayList<Person> players;
    private Scanner input;
    private BufferedReader br;
    private boolean gameOver;
    private boolean playerTurn;
    private Random random;
    private String rules;
    private SquareWallConfigs squareWallConfigs;
    private BoardDisplay boardDisplay;

    // EFFECTS: runs the teller application
    public DefenderOfDeshmelApp() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGame() {
        boolean startGame;

        init();
        while (true) {
            startGame = runMainMenu();
            while (startGame) {
                if (gameOver) {
                    resetBoard(board);
                    gameOver = false;
                    break;
                }
                if (playerTurn) {
                    boolean toContinue = takePlayerTurn();
                    if (!toContinue) {
                        break;
                    }
                } else {
                    enemyTurn(enemies.get(random.nextInt(enemies.size())));
                    boardDisplay.displayBoard();
                }
            }
        }
    }

    //EFFECTS: returns false if the player chooses not to take their turn and quit, true otherwise, processes command
    private boolean takePlayerTurn() {
        displayMenu();
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("q")) {
            saveGame();
            return false;
        } else {
            processCommand(command);
        }
        return true;
    }

    //MODIFIES: board. savedGame
    //EFFECTS: clears all characters off the board, fills each square with null, and sets a new wall config,
    //         removes save from file
    private void resetBoard(Board board) {
        board.getBoard().clear();
        board.fillBoardWithNull();
        board.setWallConfig(squareWallConfigs.getWalls());
        clearSave();
    }


    //EFFECTS: displays the main menu and processes a command given by the user
    private boolean runMainMenu() {
        displayMainMenu();
        String command = input.next();
        command = command.toLowerCase();
        return processMainMenuCommand(command);
    }

    //MODIFIES: this
    //EFFECTS: processes user main menu command
    private boolean processMainMenuCommand(String command) {
        if (command.equals("e")) {
            addEnemies(3);
        } else if (command.equals("m")) {
            addEnemies(6);
        } else if (command.equals("h")) {
            addEnemies(10);
        } else if (command.equals("l")) {
            return loadGame();
        } else if (command.equals("q")) {
            System.out.println("\nThank you for playing!");
            System.exit(0);
            return false;
        } else {
            System.out.println("Selection not valid");
            return false;
        }
        addPlayers();
        playerTurn = true;
        return true;
    }

    //MODIFIES: this
    //EFFECTS: loads game from the GAME_SAVE_FILE. returns true if the game is loaded, false if the user needs to start
    //         a new one
    private boolean loadGame() {
        Reader reader = new Reader();
        try {
            reader.readFile(new File(GAME_SAVE_FILE));
            board.setBoard(reader.getBoardState());
            enemies = reader.getEnemies();
            players = reader.getPlayers();
            int wallConfigNum = reader.getWallConfigNumber();
            board.setWallConfig(SquareWallConfigs.generateRandomWallSet(wallConfigNum));
            System.out.println("Game Loaded");
            boardDisplay.displayBoard();
            return true;
        } catch (IOException e) {
            System.out.println("I'm sorry, your game cannot be loaded. Please start a new game");
            return false;
        } catch (NullPointerException | JsonSyntaxException e) {
            System.out.println("There is no game to load. Please start a new game");
            return false;
        }
    }

    //EFFECTS: saves the game to the GAME_SAVE_FILE if no exception thrown
    private void saveGame() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.write(board, players, enemies, SquareWallConfigs.getWallSetNum());
        } catch (IOException e) {
            System.out.println("uh oh, your game could not be saved.");
        }
    }

    //EFFECTS: clears the current save from GAME_SAVE_FILE if no exception thrown
    private void clearSave() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE));
            writer.clearSave();
        } catch (IOException e) {
            System.out.println("Something went wrong cleaning up old data. Keep on playing");
        }
    }

    //REQUIRES: i = 3, 6, or 10
    //MODIFIES: this
    //EFFECTS: clears out all enemies, and adds i new enemies to enemies
    private void addEnemies(int i) {
        enemies.clear();
        if (i >= 3) {
            enemies.add(new Enemy("Foot Soldier"));
            enemies.add(new Enemy("Ranged Shooter"));
            //add a third enemy
            //TODO: design 8 more enemies
        }
        if (i >= 6) {
            //add 3 more enemies
        }
        if (i == 10) {
            //add 4 more enemies
        }
    }

    //MODIFIES: this
    //EFFECTS: clears out players, and adds the persons to players (resets the list to default state)
    private void addPlayers() {
        players.clear();
        players.add(new Person("Fire Sorceress"));
        players.add(new Person("Ice Sorcerer"));
    }

    //EFFECTS: displays the main menu options to the user
    private void displayMainMenu() {
        System.out.println("Welcome to Defenders of Deshmel");
        System.out.println("\nSelect from:");
        System.out.println("\te -> new easy game");
        System.out.println("\tm -> new medium game");
        System.out.println("\th -> new hard game");
        System.out.println("\tl -> load previous game");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            displayRemainingCharacters();
            boardDisplay.displayBoard();
            playerTurn = false;
        } else if (command.equals("m")) {
            moveCharacter();
            boardDisplay.displayBoard();
            playerTurn = false;
        } else if (command.equals("x")) {
            attackAction();
            checkGameOver();
            boardDisplay.displayBoard();
            playerTurn = false;
        } else if (command.equals("s")) {
            if (specialAction()) {
                playerTurn = false;
            }
            checkGameOver();
            boardDisplay.displayBoard();
        } else {
            displays(command);
        }
    }

    //EFFECTS: processes user input for displaying information
    private void displays(String command) {
        if (command.equals("h")) {
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
        playerTurn = true;
        board = new Board();
        boardDisplay = new BoardDisplay(board);
        random = new Random();
        squareWallConfigs = new SquareWallConfigs();
        enemies = new ArrayList<>();
        // need to get a real list together of enemies and player characters, add here

        players = new ArrayList<>();

        input = new Scanner(System.in);

        readInHelp();

        boardDisplay.displayBoard();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add character");
        System.out.println("\tm -> move character");
        System.out.println("\tx -> attack");
        System.out.println("\ts -> special action");
        System.out.println("\th -> help menu");
        System.out.println("\td -> display character stats");
        System.out.println("\tq -> save and quit");
    }



    //REQUIRES: enemy is not null
    //MODIFIES: this
    //EFFECTS: Selects and enemy action and performs it, printing out a message to indicate to the player the action
    //         either: adds enemy to the board in a random unoccupied location
    //                 attacks a player in range
    //                 moves the enemy toward a player if possible
    private void enemyTurn(Enemy enemy) {
        Action action = enemy.decideAction(board);
        if (action == Action.ADD) {
            while (true) {
                boolean successful = board.addCharacter(random.nextInt(25), enemy);
                if (successful) {
                    System.out.println("Enemy " + enemy.getName() + " has been added to the board.");
                    break;
                }
            }
        } else if (action == Action.ATTACK) {
            Person defender = enemy.canAttackPerson(board);
            attack(enemy, defender);
            checkGameOver();
        } else {
            enemyMoveInDirection(action, enemy);
        }
        playerTurn = true;
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

    //EFFECTS: reads in rules from the rules.txt file
    private void readInHelp() {
        File rulesFile = new File("data/rules.txt");
        try {
            br = new BufferedReader(new FileReader(rulesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("I'm sorry I guess you'll have to play without rules");
        }

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
            rules += readingRules + "\n";
        }
    }

    //EFFECTS: displays rules from the rules.txt file
    private void displayHelp() {
        System.out.println(rules);
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
                if (player == null || player.isEnemy() || enemy == null) {
                    System.out.println("Make sure both characters selected are on the board and yours to control.");
                } else {
                    break;
                }
            }
        }
        attack(player, enemy);
    }

    //MODIFIES: this
    //EFFECTS: allows the user to select a character and triggers their special action if they have any remaining,
    //         removes any enemies killed by the special action, returns true if action used, false otherwise
    private boolean specialAction() {
        String command;

        System.out.println("Enter the codes for the character you want to trigger the special action from: ");
        command = input.next();
        command = command.toUpperCase();

        Person player = board.findPersonByCharacterCode(command);
        if (player == null) {
            System.out.println("That player is not on the board");
        } else if (!player.hasChargesRemaining()) {
            System.out.println("That player has already used their special ability");
        } else {
            player.specialAction(board);
            System.out.println(player.getName() + " has taken a special action: " + player.getSpecialActionString());
            removeDeadEnemies();
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: removes dead enemies from the board and enemy list
    private void removeDeadEnemies() {
        ArrayList<Person> boardState = board.getBoard();
        for (int i = 0; i < 25; i++) {
            if (boardState.get(i) != null && boardState.get(i).isDead()) {
                boardState.set(i, null);
            }
        }
        enemies.removeIf(Person::isDead);
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

    //REQUIRES: direction supplied by user must be an integer between 0 and 3
    //MODIFIES: board
    //EFFECTS: prompts character for a user and direction, and moves that character if both are valid inputs
    private void moveCharacter() {
        boolean successful = false;

        while (!successful) {
            System.out.println("Enter the character code for the player you wish to move: ");
            String command = input.next();
            command = command.toUpperCase();

            Person person = board.findPersonByCharacterCode(command);
            if (person == null) {
                System.out.println("That character is not on the board. Please try again");
            } else {
                System.out.println("Enter the number of the direction to move, 0 = Left, 1 = Right, 2 = Up, 3 = Down:");
                command = input.next();
                int direction;
                try {
                    direction = Integer.parseInt(command);
                } catch (NumberFormatException e) {
                    direction = -1;
                }
                successful = board.moveCharacter(direction, person);
                if (!successful) {
                    System.out.println("That character is unable to move in that direction.");
                }
            }
        }
    }

    //REQUIRES: enemy is not null, action not null
    //MODIFIES: board
    //EFFECTS: moves enemy in the direction specified by the action
    private void enemyMoveInDirection(Action action, Enemy enemy) {
        String statement = enemy.getName() + " has moved ";
        if (action == Action.MOVE_LEFT) {
            board.moveCharacter(Board.LEFT, enemy);
            System.out.println(statement + "left");
        } else if (action == Action.MOVE_RIGHT) {
            board.moveCharacter(Board.RIGHT, enemy);
            System.out.println(statement + "right");
        } else if (action == Action.MOVE_UP) {
            board.moveCharacter(Board.UP, enemy);
            System.out.println(statement + "up");
        } else if (action == Action.MOVE_DOWN) {
            board.moveCharacter(Board.DOWN, enemy);
            System.out.println(statement + "down");
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
            Person person = getPersonCodeFromUser();
            if (person == null) {
                System.out.println("Please enter an available person code");
            } else {
                System.out.println("Enter the number of the square you want to add to (1-25 by row): ");
                command = input.next();
                try {
                    int squareNum = Integer.parseInt(command);
                    if (board.addCharacter(squareNum - 1, person)) {
                        break;
                    }
                    System.out.println("The square you want to add to is full, please try again");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a square number that is on the board");
                } catch (NumberFormatException e) {
                    System.out.println("please enter a number between 1 - 25 inclusive");
                }
            }
        }
    }

    //EFFECTS: returns the Person associated with the code or null if no person with that code
    //TODO: throw exception instead of returning null?
    private Person getPersonCodeFromUser() {
        System.out.println("Enter the character code you wish to add to the board: ");
        String command = input.next();
        command = command.toUpperCase();

        Person person = null;
        for (Person player : players) {
            if (player.getCharacterCode().equals(command) && player.isAvailable()) {
                person = player;
            }
        }
        return person;
    }
}
