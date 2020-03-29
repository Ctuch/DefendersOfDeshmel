package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static ui.DefenderOfDeshmelDisplay.MENU_WIDTH;

//panel that displays all the play (game menu) options for the user
public class GameMenuPanel extends MenuPanel {

    //EFFECTS: sets the layout of the panel, adds its title and buttons
    public GameMenuPanel(ArrayList<JButton> gameMenuButtons, JLabel displayLabel) {
        super(gameMenuButtons, 5);
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
}
