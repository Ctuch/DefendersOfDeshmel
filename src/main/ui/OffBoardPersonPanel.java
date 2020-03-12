package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OffBoardPersonPanel extends JPanel {

    private static int HORIZ_TEXT_ADJUSTMENT;
    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;

    public OffBoardPersonPanel(ArrayList<Person> players, ArrayList<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        setPreferredSize(new Dimension(BoardPanel.BOARD_WIDTH, 100));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacters(g);
    }

    private void drawCharacters(Graphics g) {
        drawPlayers(g);
        drawEnemies(g);
    }

    private void drawEnemies(Graphics g) {
        int count = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAvailable()) {
                g.setColor(Colors.ENEMY);
                g.fillOval(getWidth() / 2 + count * BoardPanel.SQUARE_SPACING, getHeight() / 4,
                        BoardPanel.SQUARE_HEIGHT, BoardPanel.SQUARE_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawString(enemy.getCharacterCode(),
                        getWidth() / 2 + count * BoardPanel.SQUARE_SPACING + (BoardPanel.SQUARE_HEIGHT / 2)
                                - BoardPanel.HORIZ_TEXT_ADJUSTMENT,
                        getHeight() / 2 + BoardPanel.VERT_TEXT_ADJUSTMENT);
                count++;
            }
        }
    }

    private void drawPlayers(Graphics g) {
        int count = 0;
        for (Person player : players) {
            if (player.isAvailable()) {
                g.setColor(Colors.PLAYER);
                g.fillOval(count * BoardPanel.SQUARE_SPACING, getHeight() / 4,
                        BoardPanel.SQUARE_HEIGHT, BoardPanel.SQUARE_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawString(player.getCharacterCode(), count * BoardPanel.SQUARE_SPACING
                                + (BoardPanel.SQUARE_HEIGHT / 2) - BoardPanel.HORIZ_TEXT_ADJUSTMENT,
                        getHeight() / 2 + BoardPanel.VERT_TEXT_ADJUSTMENT);
                count++;
            }
        }
    }

}
