package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static ui.DefenderOfDeshmelDisplay.MENU_WIDTH;

public class GameMenuPanel extends JPanel {

    private ArrayList<JButton> buttons;

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

    private void createGameMenuButtons() {
        for (JButton button : buttons) {
            add(button);
        }
    }

    private void createMenuLabel() {
        JLabel mainMenu = new JLabel("Game Menu");
        mainMenu.setPreferredSize(new Dimension(MENU_WIDTH,
                BoardPanel.BOARD_HEIGHT / 6));
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setHorizontalAlignment(JLabel.CENTER);
        add(mainMenu);
    }

    private void createDisplayLabel(JLabel displayLabel) {
        JScrollPane displayPane = new JScrollPane(displayLabel, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        displayPane.setPreferredSize(new Dimension(MENU_WIDTH, HEIGHT / 5));
        displayLabel.setBackground(Color.GREEN);
        displayLabel.setOpaque(true);
        add(displayPane);
    }
}
