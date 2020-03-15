package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//panel that displays all the play (main menu) options for the user
public class MainMenuPanel extends JPanel {

    //TODO: create a class that both this and game menu panel extend
    private ArrayList<JButton> buttons;
    private FileManager fileManager;

    //EFFECTS: sets the layout of the panel, adds its title and buttons
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

    //MODIFIES: this
    //EFFECTS: adds buttons to this and sets the load button state
    private void createMainMenuButtons() {
        for (JButton button : buttons) {
            add(button);
        }
        setLoadButtonState();
    }

    //MODIFIES: this, loadButton
    //EFFECTS: If there is a save, enables the load button, otherwise it is disabled
    public void setLoadButtonState() {
        JButton loadButton = buttons.get(3);
        if (fileManager.checkIfGameToLoad()) {
            loadButton.setEnabled(true);
        } else {
            loadButton.setEnabled(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a main menu label to display on the panel
    private void createMenuLabel() {
        JLabel mainMenu = new JLabel("Main Menu");
        mainMenu.setPreferredSize(new Dimension(DefenderOfDeshmelDisplay.MENU_WIDTH,
                BoardPanel.BOARD_HEIGHT / 6));
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setHorizontalAlignment(JLabel.CENTER);
        add(mainMenu);
    }

}
