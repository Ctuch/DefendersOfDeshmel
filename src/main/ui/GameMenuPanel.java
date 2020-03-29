package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static ui.DefenderOfDeshmelDisplay.MENU_WIDTH;

//panel that displays all the play (game menu) options for the user
public class GameMenuPanel extends MenuPanel {

    //EFFECTS: sets the layout of the panel, adds its title and buttons
    public GameMenuPanel(ActionListener listener, JLabel displayLabel) {
        super(listener, 5);
        setVisible(false);

        createMenuLabel("Game Menu");
        createMenuButtons();
        createDisplayLabel(displayLabel);
    }

    //MODIFIES: this
    //EFFECTS: creates a display label to add to the panel with scrolling so user can view all text
    private void createDisplayLabel(JLabel displayLabel) {
        JScrollPane displayPane = new JScrollPane(displayLabel, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        displayPane.setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT / 5));
        displayLabel.setBackground(Color.GREEN);
        displayLabel.setOpaque(true);
        add(displayPane);
    }

    // EFFECTS: creates the game menu buttons and adds an action listener to each, returning the list of buttons
    @Override
    protected ArrayList<JButton> createMenuButtons(ActionListener listener) {
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

        addActionListener(gameMenuButtons, listener);

        return gameMenuButtons;
    }
}
