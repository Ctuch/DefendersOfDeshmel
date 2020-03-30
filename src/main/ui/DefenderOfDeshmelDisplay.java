package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.GameComponents.*;
import static ui.Sound.*;

//Main panel for displaying application into a GUI using Swing.
//Controls background actions based on action listeners linked to associated buttons
public class DefenderOfDeshmelDisplay extends JFrame {
    protected static final int MENU_WIDTH = 200;
    private static final int WIDTH = 7 * BoardPanel.SQUARE_SPACING;
    private static final int HEIGHT = 7 * BoardPanel.SQUARE_SPACING;

    private BoardPanel boardPanel;
    private OffBoardPersonPanel personPanel;
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
        //initializes game components
        GameComponents game = new GameComponents();
        displayLabel = new JLabel();
        enemyInteractionController = new EnemyInteractionController(displayLabel);
        fileManager = new FileManager(displayLabel);
        soundPlayer = new SoundPlayer(displayLabel);
        createTimer();
    }

    private void createTimer() {
        timer = new Timer(1000, e -> {
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
        boardPanel = new BoardPanel();
        personPanel = new OffBoardPersonPanel();
        rulesPanel = new RulesPanel(displayLabel);
        mainMenuPanel = new MainMenuPanel(new MainMenuButtonActionListener(), fileManager);
        gameMenuPanel = new GameMenuPanel(new GameMenuButtonActionListener(), displayLabel);
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

    //MODIFIES: this
    //EFFECTS: selects and runs the method associated with the triggered action from the game buttons
    protected class GameMenuButtonActionListener implements ActionListener {
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
            int squareToAdd = boardPanel.getSelectedSquare2nd();
            if (squareToAdd != BoardPanel.INVALID) {
                //TODO: have feedback for the user if unsuccessful?
                if (getGameBoard().addCharacter(squareToAdd, playerToAdd)) {
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
            Person person = getGameBoard().getBoard().get(squareFrom);
            if (person != null && !person.isEnemy() && getGameBoard().moveCharacter(squareFrom, squareTo)) {
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
            int enemyNumber = getEnemies().size();
            if (enemyInteractionController.attack(squareFrom, squareTo)) {
                if (enemyNumber - getEnemies().size() == 1) {
                    soundPlayer.playSound(DEAD);
                }

                updatePanelsWithEnemyTurn();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the first selected square has a player, that isnt null and has charges remaining,
    //          triggers special action, player sound and enemy turn
    private void specialAction() {
        int squareOfPerson = boardPanel.getSelectedSquare2nd();
        Person person = getGameBoard().getBoard().get(squareOfPerson);
        if (person != null && person.hasChargesRemaining()) {
            person.specialAction(getGameBoard());
            soundPlayer.playSound(SPECIAL_ACTION);
            updatePanelsWithEnemyTurn();
        }
    }

    //MODIFIES: this
    //EFFECTS: if the first selected square has a person,
    //          displays character stats in the display label
    private void displayCharacter() {
        Person toDisplay = selectedPlayer;
        if (toDisplay != null) {
            displayLabel.setText(toDisplay.toStringHtml());
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
        Enemy.addEnemies(difficulty, getEnemies());
        Person.addPlayers(getPlayers());
        personPanel.setGameOver(0);
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
            if (getEnemies().isEmpty()) {
                personPanel.setGameOver(1);
                soundPlayer.playSound(WIN_PLAYER);
            } else if (getPlayers().isEmpty()) {
                personPanel.setGameOver(2);
                soundPlayer.playSound(WIN_ENEMY);
            }
            fileManager.clearSave();
            resetGame();
        }
        return getEnemies().isEmpty();
    }

    //MODIFIES: this
    //EFFECTS: resets all game elements to their default state, and the load button based on if there is a save
    private void resetGame() {
        switchMenuPanel();
        getGameBoard().resetBoard();
        getPlayers().clear();
        getEnemies().clear();
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
