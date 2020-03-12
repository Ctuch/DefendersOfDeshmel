package ui;

import model.Board;
import model.Person;
import model.SquareWall;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final Color WALL_COLOR = new Color(38, 34, 44);
    private static final Color SQUARE_COLOR = new Color(181, 181, 181);
    private static final Color ENEMY_COLOR = new Color(169, 55, 49);
    private static final Color PLAYER_COLOR = new Color(30, 153, 226);
    private static final int SQUARE_SPACING = 60;
    private static final int SQUARE_HEIGHT = 50;
    private static final int VERT_TEXT_ADJUSTMENT = 5;
    private static final int HORIZ_TEXT_ADJUSTMENT = 7;

    private Board board;

    public BoardPanel(Board board) {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GRAY);
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        drawEmptyBoard(g);

    }

    private void drawEmptyBoard(Graphics g) {
        SquareWall wall;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                wall = board.getWallConfig().get(5 * i + j);
                Person person = board.getBoard().get(5 * i + j);
                drawSquare(i, j, g);
                drawWall(i, j, g, wall);
                drawPerson(i, j, g, person);
            }
        }

    }

    private void drawPerson(int rowY, int colX, Graphics g, Person person) {
        if (!(person == null)) {
            if (person.isEnemy()) {
                g.setColor(ENEMY_COLOR);
            } else {
                g.setColor(PLAYER_COLOR);
            }
            g.fillOval((colX + 1) * SQUARE_SPACING,(rowY + 1) * SQUARE_SPACING, SQUARE_HEIGHT, SQUARE_HEIGHT);
            g.setColor(Color.BLACK);
            g.drawString(person.getCharacterCode(),
                    (colX + 1) * SQUARE_SPACING + (SQUARE_HEIGHT / 2) - HORIZ_TEXT_ADJUSTMENT,
                    (rowY + 1) * SQUARE_SPACING + (SQUARE_HEIGHT / 2) + VERT_TEXT_ADJUSTMENT);
        }
    }

    private void drawWall(int i, int j, Graphics g, SquareWall wall) {
        g.setColor(WALL_COLOR);
        if (wall.isRightWall()) {
            drawRightWall(i, j, g);
        }
        if (wall.isLeftWall()) {
            drawLeftWall(i, j, g);
        }
        if (wall.isLowerWall()) {
            drawLowerWall(i, j, g);
        }
        if (wall.isUpperWall()) {
            drawUpperWall(i, j, g);
        }
    }

    private void drawRightWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING + SQUARE_HEIGHT, (rowY + 1) * SQUARE_SPACING,
                SQUARE_SPACING - SQUARE_HEIGHT, SQUARE_HEIGHT);
    }

    private void drawLeftWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING - (SQUARE_SPACING - SQUARE_HEIGHT), (rowY + 1) * SQUARE_SPACING,
                SQUARE_SPACING - SQUARE_HEIGHT, SQUARE_HEIGHT);
    }

    private void drawLowerWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING + SQUARE_HEIGHT,
                SQUARE_HEIGHT, SQUARE_SPACING - SQUARE_HEIGHT);
    }

    private void drawUpperWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING - (SQUARE_SPACING - SQUARE_HEIGHT),
                SQUARE_HEIGHT, SQUARE_SPACING - SQUARE_HEIGHT);
    }

    private void drawSquare(int rowY, int colX, Graphics g) {
        g.setColor(SQUARE_COLOR);
        g.fillRect((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING, SQUARE_HEIGHT, SQUARE_HEIGHT);
    }
}
