package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuPanel extends JPanel {

    private ArrayList<JButton> buttons;
    private FileManager fileManager;

    public MainMenuPanel(ArrayList<JButton> mainMenuButtons, Board board,
                         ArrayList<Person> players, ArrayList<Enemy> enemies) {
        setPreferredSize(new Dimension(DefenderOfDeshmelDisplay.MENU_WIDTH, HEIGHT));
        setLayout(new GridLayout(0, 1, 20, 20));
        setBackground(Color.PINK);
        createMenuLabel();
        buttons = mainMenuButtons;
        fileManager = new FileManager(board, players, enemies);

        createMainMenuButtons();
    }

    private void createMainMenuButtons() {
        for (JButton button : buttons) {
            add(button);
        }
        setLoadButtonState();
    }

    public void setLoadButtonState() {
        JButton loadButton = buttons.get(3);
        if (fileManager.checkIfGameToLoad()) {
            loadButton.setEnabled(true);
        } else {
            loadButton.setEnabled(false);
        }
    }

    private void createMenuLabel() {
        JLabel mainMenu = new JLabel("Main Menu");
        mainMenu.setPreferredSize(new Dimension(DefenderOfDeshmelDisplay.MENU_WIDTH,
                BoardPanel.BOARD_HEIGHT / 6));
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setHorizontalAlignment(JLabel.CENTER);
        add(mainMenu);
    }

}
