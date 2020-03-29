package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//panel that displays all the play (main menu) options for the user
public class MainMenuPanel extends MenuPanel {

    private FileManager fileManager;

    //EFFECTS: sets the layout of the panel, adds its title and buttons
    public MainMenuPanel(ActionListener listener, Board board,
                         ArrayList<Person> players, ArrayList<Enemy> enemies, JLabel displayLabel) {
        super(listener, 20);
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

    // EFFECTS: creates the main menu buttons and adds an action listener to each, returning the list of buttons
    @Override
    protected ArrayList<JButton> createMenuButtons(ActionListener listener) {
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
        addActionListener(mainMenuButtons, listener);
        return mainMenuButtons;
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
