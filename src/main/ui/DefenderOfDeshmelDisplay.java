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
    private static Boolean playerTurn = true;

    public DefenderOfDeshmelDisplay() {
        super("Defender's Of Deshmel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        board = new Board();
        enemies = new ArrayList<>();
        players = new ArrayList<>();

        boardPanel = new BoardPanel(board);
        personPanel = new OffBoardPersonPanel(players, enemies);
        mainMenuPanel = new JPanel();
        gameMenuPanel = new JPanel();
        mainMenuPanel = createMainMenu();
        gameMenuPanel = createGameMenu();

        add(boardPanel);
        add(personPanel, BorderLayout.SOUTH);
        add(gameMenuPanel, BorderLayout.EAST);
        gameMenuPanel.setVisible(false);
        add(mainMenuPanel, BorderLayout.WEST);


        pack();
        centreOnScreen();
        setVisible(true);
        //addTimer();
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
        gameMenuPanel.setLayout(new GridLayout(0, 1, 20, 20));
        gameMenuPanel.setBackground(Color.BLUE);

        createGameMenuButtons(gameMenuPanel);
        return gameMenuPanel;
    }

    private void createGameMenuButtons(JComponent parent) {
        JButton addButton = new JButton("Add Character");
        JButton moveButton = new JButton("Move Character");

        parent.add(addButton);
        parent.add(moveButton);

        addButton.addActionListener(new GameMenuButtonActionListener());
        moveButton.addActionListener(new GameMenuButtonActionListener());
    }

    private JPanel createMainMenu() {
        mainMenuPanel.setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT));
        mainMenuPanel.setLayout(new GridLayout(0, 1, 20, 20));
        mainMenuPanel.setBackground(Color.PINK);

        createMainMenuLabel(mainMenuPanel);
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

    private void createMainMenuLabel(JComponent parent) {
        JLabel mainMenu = new JLabel("Main Menu");
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
            }
        }
    }

    private void moveCharacter() {
        int squareFrom = boardPanel.getSelectedSquare1st();
        int squareTo = boardPanel.getSelectedSquare2nd();
        if (squareFrom != -1 && squareTo != -1) {
            board.moveCharacter(squareFrom, squareTo);
            boardPanel.repaint();
        }
    }

    private void addCharacter() {
        Person playerToAdd = personPanel.getSelectedPlayer();
        if (playerToAdd != null) {
            //TODO: have them select the square after button? Ie a timer to have them select a square...
            int squareToAdd = boardPanel.getSelectedSquare2nd();
            if (squareToAdd != -1) {
                //TODO: have feedback for the user if unsuccessful?
                board.addCharacter(squareToAdd, playerToAdd);
                boardPanel.repaint();
                personPanel.repaint();
            }
        }
    }

    public void startNewGame(Difficulty difficulty) {
        Enemy.addEnemies(difficulty, enemies);
        Person.addPlayers(players);
        playerTurn = true;
        personPanel.repaint();
        switchToInGamePanel();
    }

    private void switchToInGamePanel() {
        if (mainMenuPanel.isVisible()) {
            mainMenuPanel.setVisible(false);
            gameMenuPanel.setVisible(true);
        } else {
            mainMenuPanel.setVisible(true);
            gameMenuPanel.setVisible(false);
        }
    }


}
