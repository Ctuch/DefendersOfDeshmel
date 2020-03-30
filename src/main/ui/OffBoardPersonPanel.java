package ui;

import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.GameComponents.*;

//panel for displaying characters not currently on the board
public class OffBoardPersonPanel extends JPanel {

    private int gameOver = 0;
    private Person selectedPlayer;

    //EFFECTS: sets the panel layout, initializes variables, and adds mouse control to the panel
    public OffBoardPersonPanel() {
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
                selectedPlayer = selectionManager.updateSelectedPlayer(e.getX(), e.getY(), getPlayers());
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

        if (getEnemies().isEmpty() && getPlayers().isEmpty() && gameOver >= 1) {
            gameOver(g);
        }
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
        for (Enemy enemy : getEnemies()) {
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
        for (Person player : getPlayers()) {
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

    //MODIFIES: g
    //EFFECTS: displays the game over and winner or loser strings
    private void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 20, 15));
        FontMetrics fm = g.getFontMetrics();
        centerString("Game Over!", g, fm, getHeight() / 2);

        String winning;
        if (gameOver == 1) {
            winning = "You won!";
        } else {
            winning = "You lost.";
        }
        centerString(winning, g, fm, getHeight() / 2 + 20);
    }

    // Centers a string on the screen
    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    // code from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase/
    //         specifically the GamePanel.java class in the UI package
    private void centerString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (getWidth() - width) / 2, posY);
    }

    public Person getSelectedPlayer() {
        return selectedPlayer;
    }


    public void setGameOver(int gameOver) {
        this.gameOver = gameOver;
    }
}
