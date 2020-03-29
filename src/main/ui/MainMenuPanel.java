package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.util.ArrayList;

//panel that displays all the play (main menu) options for the user
public class MainMenuPanel extends MenuPanel {

    private FileManager fileManager;

    //EFFECTS: sets the layout of the panel, adds its title and buttons
    public MainMenuPanel(ArrayList<JButton> mainMenuButtons, Board board,
                         ArrayList<Person> players, ArrayList<Enemy> enemies, JLabel displayLabel) {
        super(mainMenuButtons, 20);
        createMenuLabel("Main Menu");
        fileManager = new FileManager(board, players, enemies, displayLabel);

        createMenuButtons();
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to this and sets the load button state
    @Override
    protected void createMenuButtons() {
        super.createMenuButtons();
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
}
