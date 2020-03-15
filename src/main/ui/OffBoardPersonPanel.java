package ui;

import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//panel for displaying characters not currently on the board
public class OffBoardPersonPanel extends JPanel {

    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;

    private Person selectedPlayer;

    //EFFECTS: sets the panel layout, initializes variables, and adds mouse control to the panel
    public OffBoardPersonPanel(ArrayList<Person> players, ArrayList<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        selectedPlayer = null;
        setPreferredSize(new Dimension(BoardPanel.BOARD_WIDTH, 100));
        setBackground(Color.DARK_GRAY);
        addMouseControl();
    }

    //MODIFIES: this
    //EFFECTS: adds a mouse listener to the board that remembers the last selected person by the user
    private void addMouseControl() {
        MouseSelectionManager selectionManager = new MouseSelectionManager();
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                selectedPlayer = selectionManager.updateSelectedPlayer(e.getX(), e.getY(), players);
                DefenderOfDeshmelDisplay.setSelectedPlayer(selectedPlayer);
            }
        });
    }

    //MODIFIES: display
    //EFFECTS: redraws the players and enemies onto the display
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacters(g);
    }

    //MODIFIES: display
    //EFFECTS: redraws the players and enemies onto the display
    private void drawCharacters(Graphics g) {
        drawPlayers(g);
        drawEnemies(g);
    }

    //MODIFIES: display
    //EFFECTS: redraws the enemies onto the display
    private void drawEnemies(Graphics g) {
        int count = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAvailable()) {
                g.setColor(Colors.ENEMY);
                g.fillOval(getWidth() / 2 + count * BoardPanel.SQUARE_SPACING, getHeight() / 4,
                        BoardPanel.SQUARE_HEIGHT, BoardPanel.SQUARE_HEIGHT);
                enemy.setLocation(getWidth() / 2 + count * BoardPanel.SQUARE_SPACING,
                        getHeight() / 4);
                g.setColor(Color.BLACK);
                g.drawString(enemy.getCharacterCode(),
                        getWidth() / 2 + count * BoardPanel.SQUARE_SPACING + (BoardPanel.SQUARE_HEIGHT / 2)
                                - BoardPanel.HORIZ_TEXT_ADJUSTMENT,
                        getHeight() / 2 + BoardPanel.VERT_TEXT_ADJUSTMENT);
                count++;
            }
        }
    }

    //MODIFIES: display
    //EFFECTS: redraws the players onto the display
    private void drawPlayers(Graphics g) {
        int count = 0;
        for (Person player : players) {
            if (player.isAvailable()) {
                g.setColor(Colors.PLAYER);
                g.fillOval(count * BoardPanel.SQUARE_SPACING, getHeight() / 4,
                        BoardPanel.SQUARE_HEIGHT, BoardPanel.SQUARE_HEIGHT);
                player.setLocation(count * BoardPanel.SQUARE_SPACING, getHeight() / 4);
                g.setColor(Color.BLACK);
                g.drawString(player.getCharacterCode(), count * BoardPanel.SQUARE_SPACING
                                + (BoardPanel.SQUARE_HEIGHT / 2) - BoardPanel.HORIZ_TEXT_ADJUSTMENT,
                        getHeight() / 2 + BoardPanel.VERT_TEXT_ADJUSTMENT);
                count++;
            }
        }
    }

    public Person getSelectedPlayer() {
        return selectedPlayer;
    }
}
