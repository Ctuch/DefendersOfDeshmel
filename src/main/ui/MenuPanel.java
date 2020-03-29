package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ui.DefenderOfDeshmelDisplay.MENU_WIDTH;

//represents a menu panel with a display of buttons for the user to interact with
public abstract class MenuPanel extends JPanel {

    protected ArrayList<JButton> buttons;

    //EFFECTS: sets the size, layout, and background color for the panel
    protected MenuPanel(ArrayList<JButton> menuButtons, int vgap) {
        setPreferredSize(new Dimension(DefenderOfDeshmelDisplay.MENU_WIDTH, HEIGHT));
        setLayout(new GridLayout(0, 1, 20, vgap));
        setBackground(Colors.MENU);
        buttons = menuButtons;
    }

    //MODIFIES: this
    //EFFECTS: creates a menu label with text to display on the panel
    protected void createMenuLabel(String text) {
        JLabel mainMenu = new JLabel(text);
        mainMenu.setPreferredSize(new Dimension(MENU_WIDTH,
                BoardPanel.BOARD_HEIGHT / 6));
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setHorizontalAlignment(JLabel.CENTER);
        add(mainMenu);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to this
    protected void createMenuButtons() {
        for (JButton button : buttons) {
            add(button);
        }
    }
}
