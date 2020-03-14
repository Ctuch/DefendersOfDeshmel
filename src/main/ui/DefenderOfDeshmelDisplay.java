package ui;

import model.Board;
import model.Difficulty;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.*;

public class DefenderOfDeshmelDisplay extends JFrame {
    private static final int MENU_WIDTH = 200;
    private static final int WIDTH = 7 * BoardPanel.SQUARE_SPACING;
    private static final int HEIGHT = 7 * BoardPanel.SQUARE_SPACING;

    private BoardPanel boardPanel;
    private OffBoardPersonPanel personPanel;
    private Board board;
    private ArrayList<Enemy> enemies;
    private ArrayList<Person> players;
    private JPanel mainMenuPanel;
    private JPanel gameMenuPanel;
    private JLabel displayLabel;
    private JPopupMenu rulesPanel;
    private Timer timer;

    private static Person selectedPlayer = null;

    private static boolean gameOver;
    private EnemyInteractionController enemyInteractionController;

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
        mainMenuPanel = new JPanel();
        gameMenuPanel = new JPanel();

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonActionListener());
        rulesPanel = new RulesPanel(closeButton);
        mainMenuPanel = createMainMenu();
        gameMenuPanel = createGameMenu();
    }

    private void initFields() {
        board = new Board();
        enemies = new ArrayList<>();
        players = new ArrayList<>();
        gameOver = false;
        enemyInteractionController = new EnemyInteractionController(board, players, enemies);

        timer = new Timer(2000, null);
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

    private JPanel createGameMenu() {
        gameMenuPanel.setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT));
        gameMenuPanel.setLayout(new GridLayout(0, 1, 20, 5));
        gameMenuPanel.setBackground(Color.BLUE);
        gameMenuPanel.setVisible(false);

        createMenuLabel(gameMenuPanel, "Game Menu");
        createGameMenuButtons(gameMenuPanel);
        createDisplayLabel(gameMenuPanel);
        return gameMenuPanel;
    }

    private void createDisplayLabel(JPanel parent) {
        JScrollPane displayPane = new JScrollPane(displayLabel, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        displayPane.setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT / 5));
        displayLabel.setBackground(Color.GREEN);
        displayLabel.setOpaque(true);
        parent.add(displayPane);
    }

    private void createGameMenuButtons(JComponent parent) {
        JButton addButton = new JButton("Add Character");
        JButton moveButton = new JButton("Move Character");
        JButton attackButton = new JButton("Attack Enemy");
        JButton specialActionButton = new JButton("Special Action");
        JButton displayButton = new JButton("Display Character Stats");
        JButton helpButton = new JButton("Display help");

        parent.add(addButton);
        parent.add(moveButton);
        parent.add(attackButton);
        parent.add(specialActionButton);
        parent.add(displayButton);
        parent.add(helpButton);

        addButton.addActionListener(new GameMenuButtonActionListener());
        moveButton.addActionListener(new GameMenuButtonActionListener());
        attackButton.addActionListener(new GameMenuButtonActionListener());
        specialActionButton.addActionListener(new GameMenuButtonActionListener());
        displayButton.addActionListener(new GameMenuButtonActionListener());
        helpButton.addActionListener(new GameMenuButtonActionListener());
    }

    private JPanel createMainMenu() {
        mainMenuPanel.setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT));
        mainMenuPanel.setLayout(new GridLayout(0, 1, 20, 20));
        mainMenuPanel.setBackground(Color.PINK);

        createMenuLabel(mainMenuPanel, "Main Menu");
        createMainMenuButtons(mainMenuPanel);
        return mainMenuPanel;
    }

    private void createMainMenuButtons(JComponent parent) {
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton loadButton = new JButton("Load previous game");
        JButton quitButton = new JButton("Quit");

        parent.add(easyButton);
        parent.add(mediumButton);
        parent.add(hardButton);
        parent.add(loadButton);
        parent.add(quitButton);

        easyButton.addActionListener(new MainMenuButtonActionListener());
        mediumButton.addActionListener(new MainMenuButtonActionListener());
        hardButton.addActionListener(new MainMenuButtonActionListener());
    }

    private void createMenuLabel(JComponent parent, String title) {
        JLabel mainMenu = new JLabel(title);
        mainMenu.setPreferredSize(new Dimension(MENU_WIDTH, BoardPanel.BOARD_HEIGHT / 6));
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setHorizontalAlignment(JLabel.CENTER);
        parent.add(mainMenu);
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
            }
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
        }
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
        //timeDelay();
        enemyInteractionController.enemyTurn();
        boardPanel.repaint();
        personPanel.repaint();
    }

    private void timeDelay() {
        timer.start();
        while (timer.isRunning()) {
            //continue execution
        }
        timer.stop();
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

    public static void setGameOver(boolean gameOver) {
        DefenderOfDeshmelDisplay.gameOver = gameOver;
    }
}
