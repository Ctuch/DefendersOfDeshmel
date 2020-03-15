package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static ui.Sound.*;
//TODO: replace all SOP with display messages for player in display box

//Main panel for displaying application into a GUI using Swing.
//Controls background actions based on action listeners linked to associated buttons
public class DefenderOfDeshmelDisplay extends JFrame {
    protected static final int MENU_WIDTH = 200;
    private static final int WIDTH = 7 * BoardPanel.SQUARE_SPACING;
    private static final int HEIGHT = 7 * BoardPanel.SQUARE_SPACING;

    private BoardPanel boardPanel;
    private OffBoardPersonPanel personPanel;
    private Board board;
    private ArrayList<Enemy> enemies;
    private ArrayList<Person> players;
    private MainMenuPanel mainMenuPanel;
    private GameMenuPanel gameMenuPanel;
    private JLabel displayLabel;
    private RulesPanel rulesPanel;
    private Timer timer;
    private SoundPlayer soundPlayer;
    private static Person selectedPlayer = null;
    private EnemyInteractionController enemyInteractionController;
    private FileManager fileManager;

    //EFFECTS: creates a Jframe with a title and sets up the game
    public DefenderOfDeshmelDisplay() {
        super("Defender's Of Deshmel");
        setUpGame();
    }

    //MODIFIES: this
    //EFFECTS: initializes the frame, fields, and panels, and centers the window, setting it visible
    private void setUpGame() {
        initMainFrame();
        initFields();
        initPanels();
        addPanels();
        pack();
        centreOnScreen();
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets the size, default close operation and layout of the frame
    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }

    //MODIFIES: this
    //EFFECTS: initializes the fields, setting the timer up with an action listener to delay enemy turns
    private void initFields() {
        board = new Board();
        enemies = new ArrayList<>();
        players = new ArrayList<>();
        enemyInteractionController = new EnemyInteractionController(board, players, enemies);
        fileManager = new FileManager(board, players, enemies);
        soundPlayer = new SoundPlayer();
        createTimer();
    }

    private void createTimer() {
        timer = new Timer(500, e -> {
            enemyInteractionController.enemyTurn();
            boardPanel.repaint();
            personPanel.repaint();
            checkGameOver();
        });
        timer.setRepeats(false);
    }

    //MODIFIES: this
    //EFFECTS: initializes the panels and with the display label, creating the buttons
    private void initPanels() {
        boardPanel = new BoardPanel(board);
        personPanel = new OffBoardPersonPanel(players, enemies);
        displayLabel = new JLabel();

        rulesPanel = new RulesPanel();
        mainMenuPanel = new MainMenuPanel(createMainMenuButtons(), board, players, enemies);
        gameMenuPanel = new GameMenuPanel(createGameMenuButtons(), displayLabel);
    }

    //MODIFIES: this
    //EFFECTS: adds the panels to this
    private void addPanels() {
        add(boardPanel);
        add(personPanel, BorderLayout.SOUTH);
        add(gameMenuPanel, BorderLayout.EAST);
        add(mainMenuPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centered on desktop
    // code from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase/
    //      specifically the SpaceInvaders.java class in the UI package
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // EFFECTS: creates the game menu buttons and adds an action listener to each, returning the list of buttons
    private ArrayList<JButton> createGameMenuButtons() {
        JButton addButton = new JButton("Add Character");
        JButton moveButton = new JButton("Move Character");
        JButton attackButton = new JButton("Attack Enemy");
        JButton specialActionButton = new JButton("Special Action");
        JButton displayButton = new JButton("Display Character Stats");
        JButton helpButton = new JButton("Display help");
        JButton saveQuitButton = new JButton("Save and Quit");
        ArrayList<JButton> gameMenuButtons = new ArrayList<>();
        gameMenuButtons.add(addButton);
        gameMenuButtons.add(moveButton);
        gameMenuButtons.add(attackButton);
        gameMenuButtons.add(specialActionButton);
        gameMenuButtons.add(displayButton);
        gameMenuButtons.add(helpButton);
        gameMenuButtons.add(saveQuitButton);

        addActionListener(gameMenuButtons, new GameMenuButtonActionListener());

        return gameMenuButtons;
    }

    // EFFECTS: creates the main menu buttons and adds an action listener to each, returning the list of buttons
    private ArrayList<JButton> createMainMenuButtons() {
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton loadButton = new JButton("Load previous game");
        JButton quitButton = new JButton("Quit");
        ArrayList<JButton> mainMenuButtons = new ArrayList<>();
        mainMenuButtons.add(easyButton);
        mainMenuButtons.add(mediumButton);
        mainMenuButtons.add(hardButton);
        mainMenuButtons.add(loadButton);
        mainMenuButtons.add(quitButton);
        addActionListener(mainMenuButtons, new MainMenuButtonActionListener());
        return mainMenuButtons;
    }


    // EFFECTS: adds the ActionListener listener to each button
    private void addActionListener(ArrayList<JButton> buttons, ActionListener listener) {
        for (JButton button : buttons) {
            button.addActionListener(listener);
        }
    }

    //MODIFIES: this
    //EFFECTS: selects and runs the method associated with the triggered action from the game buttons
    private class GameMenuButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equalsIgnoreCase("Add Character")) {
                addCharacter();
            } else if (command.equalsIgnoreCase("Move Character")) {
                moveCharacter();
            } else if (command.equalsIgnoreCase("Attack Enemy")) {
                attackEnemy();
            } else if (command.equalsIgnoreCase("Special Action")) {
                specialAction();
            } else if (command.equalsIgnoreCase("Display Character Stats")) {
                displayCharacter();
            } else if (command.equalsIgnoreCase("Display help")) {
                JOptionPane.showMessageDialog(null,
                        rulesPanel.getRulesScrollPane(),
                        "Rules",
                        JOptionPane.PLAIN_MESSAGE);
            } else if (command.equalsIgnoreCase("Save and Quit")) {
                saveAndQuit();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: selects and runs the method associated with the triggered action from the main menu buttons
    private class MainMenuButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equalsIgnoreCase("Easy")) {
                startNewGame(Difficulty.EASY);
            } else if (command.equalsIgnoreCase("Medium")) {
                startNewGame(Difficulty.MEDIUM);
            } else if (command.equalsIgnoreCase("Hard")) {
                startNewGame(Difficulty.HARD);
            } else if (command.equalsIgnoreCase("Load previous game")) {
                loadGame();
            } else if (command.equalsIgnoreCase("quit")) {
                quitGame();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the the selected player is not null, and the select square is valid, tries to add character
    //         if the player can be added to that square, triggers player sound and enemy turn
    private void addCharacter() {
        Person playerToAdd = personPanel.getSelectedPlayer();
        if (playerToAdd != null) {
            //TODO: have them select the square after button? Ie a timer to have them select a square...
            int squareToAdd = boardPanel.getSelectedSquare2nd();
            if (squareToAdd != BoardPanel.INVALID) {
                //TODO: have feedback for the user if unsuccessful?
                if (board.addCharacter(squareToAdd, playerToAdd)) {
                    soundPlayer.playSound(ADD);
                    updatePanelsWithEnemyTurn();
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if both panels are valid and first one has a player on it, tries to move character
    //         if the player can move in that direction, triggers player sound and enemy turn
    private void moveCharacter() {
        int squareFrom = boardPanel.getSelectedSquare1st();
        int squareTo = boardPanel.getSelectedSquare2nd();
        if (squareFrom != BoardPanel.INVALID && squareTo != BoardPanel.INVALID) {
            Person person = board.getBoard().get(squareFrom);
            if (person != null && !person.isEnemy() && board.moveCharacter(squareFrom, squareTo)) {
                soundPlayer.playSound(MOVE);
                updatePanelsWithEnemyTurn();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the first selected square has a character and the second selected square has a player,
    //         tries to attack from 2nd to 1st.
    //         if the player can be attack, attacks, triggers player sound and enemy turn
    private void attackEnemy() {
        int squareFrom = boardPanel.getSelectedSquare1st();
        int squareTo = boardPanel.getSelectedSquare2nd();
        if (squareFrom != BoardPanel.INVALID && squareTo != BoardPanel.INVALID) {
            if (enemyInteractionController.attack(squareFrom, squareTo)) {
                soundPlayer.playSound(EXPLOSION);
                updatePanelsWithEnemyTurn();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the first selected square has a player, that isnt null and has charges remaining,
    //          triggers special action, player sound and enemy turn
    private void specialAction() {
        int squareOfPerson = boardPanel.getSelectedSquare2nd();
        Person person = board.getBoard().get(squareOfPerson);
        if (person != null && person.hasChargesRemaining()) {
            person.specialAction(board);
            updatePanelsWithEnemyTurn();
        }
    }

    //MODIFIES: this
    //EFFECTS: if the first selected square has a person,
    //          displays character stats in the display label
    private void displayCharacter() {
        Person toDisplay = selectedPlayer;
        if (toDisplay != null) {
            displayLabel.setText(toDisplay.toString());
        } else {
            displayLabel.setText("");
        }
    }

    //MODIFIES: save file, this
    //EFFECTS: saves the game to the save file and resets all game elements
    private void saveAndQuit() {
        fileManager.saveGame();
        resetGame();
    }

    //MODIFIES: this, enemies, players
    //EFFECTS: adds all of the enemies and players to their lists based on the difficulty and to the display,
    //         switching to the game panel
    public void startNewGame(Difficulty difficulty) {
        Enemy.addEnemies(difficulty, enemies);
        Person.addPlayers(players);
        personPanel.repaint();
        switchMenuPanel();
    }

    //MODIFIES: this
    //EFFECTS: loads in the game from the save file, updating the display and switching to game panel
    private void loadGame() {
        if (fileManager.loadGame()) {
            boardPanel.repaint();
            personPanel.repaint();
            switchMenuPanel();
        }
    }

    //EFFECTS: quits out of the window closing this (the GUI)
    private void quitGame() {
        this.dispose();
    }

    //MODIFIES: this
    //EFFECTS: if either side has defeated all of the other, ends the game playing the appropriate sound,
    //         clearing out any saves and resetting the game back to the main menu
    private Boolean checkGameOver() {
        if (enemyInteractionController.checkGameOver()) {
            if (enemies.isEmpty()) {
                soundPlayer.playSound(WIN_PLAYER);
            } else if (players.isEmpty()) {
                soundPlayer.playSound(WIN_ENEMY);
            }
            fileManager.clearSave();
            resetGame();
        }
        return enemies.isEmpty();
    }

    //MODIFIES: this
    //EFFECTS: resets all game elements to their default state, and the load button based on if there is a save
    private void resetGame() {
        switchMenuPanel();
        board.resetBoard();
        players.clear();
        enemies.clear();
        boardPanel.repaint();
        personPanel.repaint();
        mainMenuPanel.setLoadButtonState();
    }

    //MODIFIES: this
    //EFFECTS: if the players have not won the game,
    //         takes the enemy turn with a slight delay to allow the user to register the action
    private void updatePanelsWithEnemyTurn() {
        boolean didWinPlayer = checkGameOver();
        if (!didWinPlayer) {
            boardPanel.repaint();
            personPanel.repaint();
            timer.start();
        }
    }

    //MODIFIES: this
    //EFFECTS: switches which menu panel is visible
    private void switchMenuPanel() {
        mainMenuPanel.setVisible(!mainMenuPanel.isVisible());
        gameMenuPanel.setVisible(!gameMenuPanel.isVisible());
    }

    public static void setSelectedPlayer(Person selectedPlayer) {
        DefenderOfDeshmelDisplay.selectedPlayer = selectedPlayer;
    }
}
