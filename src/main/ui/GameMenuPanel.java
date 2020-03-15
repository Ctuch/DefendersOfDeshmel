package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static ui.DefenderOfDeshmelDisplay.MENU_WIDTH;

//panel that displays all the play (game menu) options for the user
public class GameMenuPanel extends JPanel {

    private ArrayList<JButton> buttons;

    //EFFECTS: sets the layout of the panel, adds its title and buttons
    public GameMenuPanel(ArrayList<JButton> mainMenuButtons, JLabel displayLabel) {
        setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT));
        setLayout(new GridLayout(0, 1, 20, 5));
        setBackground(Color.BLUE);
        setVisible(false);

        createMenuLabel();
        buttons = mainMenuButtons;

        createGameMenuButtons();
        createDisplayLabel(displayLabel);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to this
    private void createGameMenuButtons() {
        for (JButton button : buttons) {
            add(button);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a game menu label to display on the panel
    private void createMenuLabel() {
        JLabel mainMenu = new JLabel("Game Menu");
        mainMenu.setPreferredSize(new Dimension(MENU_WIDTH,
                BoardPanel.BOARD_HEIGHT / 6));
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setHorizontalAlignment(JLabel.CENTER);
        add(mainMenu);
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
