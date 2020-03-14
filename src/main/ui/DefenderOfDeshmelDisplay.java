package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JPopupMenu rulesPanel;
    private Timer timer;

    private static Person selectedPlayer = null;

    private EnemyInteractionController enemyInteractionController;
    private FileManager fileManager;

    public DefenderOfDeshmelDisplay() {
        super("Defender's Of Deshmel");
        setUpGame();
    }

    private void setUpGame() {
        initMainFrame();
        initFields();
        initPanels();
        addPanels();
        pack();
        centreOnScreen();
        setVisible(true);
    }

    private void addPanels() {
        add(boardPanel);
        add(personPanel, BorderLayout.SOUTH);
        add(gameMenuPanel, BorderLayout.EAST);
        add(mainMenuPanel, BorderLayout.WEST);
    }

    private void initPanels() {
        boardPanel = new BoardPanel(board);
        personPanel = new OffBoardPersonPanel(players, enemies);
        displayLabel = new JLabel();

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonActionListener());
        rulesPanel = new RulesPanel(closeButton);
        mainMenuPanel = new MainMenuPanel(createMainMenuButtons(), board, players, enemies);
        gameMenuPanel = new GameMenuPanel(createGameMenuButtons(), displayLabel);
    }

    private void initFields() {
        board = new Board();
        enemies = new ArrayList<>();
        players = new ArrayList<>();
        enemyInteractionController = new EnemyInteractionController(board, players, enemies);
        fileManager = new FileManager(board, players, enemies);

        timer = new Timer(500, e -> {
            enemyInteractionController.enemyTurn();
            boardPanel.repaint();
            personPanel.repaint();
            checkGameOver();
        });
        timer.setRepeats(false);

    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

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

    private void addActionListener(ArrayList<JButton> buttons, ActionListener listener) {
        for (JButton button : buttons) {
            button.addActionListener(listener);
        }
    }

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

    private void quitGame() {
        this.dispose();
    }

    private void loadGame() {
        if (fileManager.loadGame()) {
            boardPanel.repaint();
            personPanel.repaint();
            switchMenuPanel();
        }
    }

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
                displayHelp();
            }

            checkGameOver();

            if (command.equalsIgnoreCase("Save and Quit")) {
                saveAndQuit();
            }
        }
    }

    private void checkGameOver() {
        if (enemyInteractionController.checkGameOver()) {
            fileManager.clearSave();
            resetGame();
        }
    }

    private void resetGame() {
        switchMenuPanel();
        board.resetBoard();
        players.clear();
        enemies.clear();
        boardPanel.repaint();
        personPanel.repaint();
        mainMenuPanel.setLoadButtonState();
    }

    private void saveAndQuit() {
        fileManager.saveGame();
        resetGame();
    }

    private void displayHelp() {
        if (rulesPanel.isVisible()) {
            remove(rulesPanel);
            rulesPanel.setVisible(false);
        } else {
            add(rulesPanel);
            rulesPanel.setVisible(true);
        }
    }

    private void displayCharacter() {
        Person toDisplay = selectedPlayer;
        if (toDisplay != null) {
            displayLabel.setText(toDisplay.toString());
        } else {
            displayLabel.setText("");
        }

    }

    private void moveCharacter() {
        int squareFrom = boardPanel.getSelectedSquare1st();
        int squareTo = boardPanel.getSelectedSquare2nd();
        if (squareFrom != -1 && squareTo != -1) {
            if (board.moveCharacter(squareFrom, squareTo)) {
                updatePanelsWithEnemyTurn();
            }
        }
    }

    private void attackEnemy() {
        int squareFrom = boardPanel.getSelectedSquare1st();
        int squareTo = boardPanel.getSelectedSquare2nd();
        if (squareFrom != -1 && squareTo != -1) {
            if (enemyInteractionController.attack(squareFrom, squareTo)) {
                updatePanelsWithEnemyTurn();
            }
        }
    }

    private void addCharacter() {
        Person playerToAdd = personPanel.getSelectedPlayer();
        if (playerToAdd != null) {
            //TODO: have them select the square after button? Ie a timer to have them select a square...
            int squareToAdd = boardPanel.getSelectedSquare2nd();
            if (squareToAdd != -1) {
                //TODO: have feedback for the user if unsuccessful?
                if (board.addCharacter(squareToAdd, playerToAdd)) {
                    updatePanelsWithEnemyTurn();
                }
            }
        }
    }

    private void specialAction() {
        int squareOfPerson = boardPanel.getSelectedSquare2nd();
        Person person = board.getBoard().get(squareOfPerson);
        if (person != null && person.hasChargesRemaining()) {
            person.specialAction(board);
            updatePanelsWithEnemyTurn();
        }
    }


    private void updatePanelsWithEnemyTurn() {
        boardPanel.repaint();
        personPanel.repaint();
        timeDelay();
    }

    private void timeDelay() {
        timer.start();
    }

    public void startNewGame(Difficulty difficulty) {
        Enemy.addEnemies(difficulty, enemies);
        Person.addPlayers(players);
        personPanel.repaint();
        switchMenuPanel();
    }

    private void switchMenuPanel() {
        if (mainMenuPanel.isVisible()) {
            mainMenuPanel.setVisible(false);
            gameMenuPanel.setVisible(true);
        } else {
            mainMenuPanel.setVisible(true);
            gameMenuPanel.setVisible(false);
        }
    }

    public static void setSelectedPlayer(Person selectedPlayer) {
        DefenderOfDeshmelDisplay.selectedPlayer = selectedPlayer;
    }

    private class CloseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            remove(rulesPanel);
            rulesPanel.setVisible(false);
        }
    }
}
