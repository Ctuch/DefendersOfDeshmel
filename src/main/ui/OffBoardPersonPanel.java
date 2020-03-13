package ui;

import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class OffBoardPersonPanel extends JPanel {

    private ArrayList<Person> players;
    private ArrayList<Enemy> enemies;

    private Person selectedPlayer;

    public OffBoardPersonPanel(ArrayList<Person> players, ArrayList<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        selectedPlayer = null;
        setPreferredSize(new Dimension(BoardPanel.BOARD_WIDTH, 100));
        setBackground(Color.DARK_GRAY);
        addMouseControl();
    }

    private void addMouseControl() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
                updateSelectedPlayer(e.getX(), e.getY());
            }
        });
    }

    private void updateSelectedPlayer(int mouseX, int mouseY) {
        for (Person player : players) {
            if (isInPlayer(mouseX, mouseY, player.getLocationX(), player.getLocationY())) {
                selectedPlayer = player;
                return;
            }
        }
        selectedPlayer = null;
    }

    private boolean isInPlayer(int mouseX, int mouseY, int locationX, int locationY) {
        int differenceX = mouseX - locationX;
        int differenceY = mouseY - locationY;
        int maxDifference = BoardPanel.SQUARE_HEIGHT;
        return differenceX <= maxDifference && differenceX >= 0 && differenceY <= maxDifference && differenceY >= 0;
    }

    public Person getSelectedPlayer() {
        return selectedPlayer;
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

    private void drawPlayers(Graphics g) {
        int count = 0;
        for (Person player : players) {
            if (player.isAvailable()) {
                g.setColor(Colors.PLAYER);
                g.fillOval(count * BoardPanel.SQUARE_SPACING, getHeight() / 4,
                        BoardPanel.SQUARE_HEIGHT, BoardPanel.SQUARE_HEIGHT);
                player.setLocation(count * BoardPanel.SQUARE_SPACING, getHeight() / 4);
                System.out.println(player.getLocationX() + ", " + player.getLocationY());
                g.setColor(Color.BLACK);
                g.drawString(player.getCharacterCode(), count * BoardPanel.SQUARE_SPACING
                                + (BoardPanel.SQUARE_HEIGHT / 2) - BoardPanel.HORIZ_TEXT_ADJUSTMENT,
                        getHeight() / 2 + BoardPanel.VERT_TEXT_ADJUSTMENT);
                count++;
            }
        }
    }

}
