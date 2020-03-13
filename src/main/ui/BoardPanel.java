package ui;

import model.Board;
import model.Person;
import model.SquareWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BoardPanel extends JPanel {

    protected static final int SQUARE_SPACING = 60;
    protected static final int SQUARE_HEIGHT = 50;
    protected static final int VERT_TEXT_ADJUSTMENT = 5;
    protected static final int HORIZ_TEXT_ADJUSTMENT = 7;
    protected static final int BOARD_WIDTH = SQUARE_SPACING * 7;
    protected static final int BOARD_HEIGHT = SQUARE_SPACING * 7;

    private Board board;
    private int selectedSquare = -1;

    public BoardPanel(Board board) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.GRAY);
        this.board = board;
        addMouseControl();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        SquareWall wall;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                wall = board.getWallConfig().get(5 * i + j);
                Person person = board.getBoard().get(5 * i + j);
                drawSquare(i, j, g, wall);
                drawWall(i, j, g, wall);
                drawPerson(i, j, g, person);
            }
        }
    }

    private void addMouseControl() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                updateSelectedSquare(e.getX(), e.getY());
            }
        });
    }

    private void updateSelectedSquare(int mouseX, int mouseY) {
        //TODO: abstract this out since copying and pasting functionality from personPanel - selector class?
        ArrayList<SquareWall> walls = board.getWallConfig();
        for (int i = 0; i < walls.size(); i++) {
            if (isInWall(mouseX, mouseY, walls.get(i).getLocationX(), walls.get(i).getLocationY())) {
                selectedSquare = i;
                return;
            }
        }
        selectedSquare = -1;
    }

    //TODO: exactly. the same as personPanel
    private boolean isInWall(int mouseX, int mouseY, int locationX, int locationY) {
        int differenceX = mouseX - locationX;
        int differenceY = mouseY - locationY;
        int maxDifference = BoardPanel.SQUARE_HEIGHT;
        return differenceX <= maxDifference && differenceX >= 0 && differenceY <= maxDifference && differenceY >= 0;
    }

    private void drawPerson(int rowY, int colX, Graphics g, Person person) {
        if (!(person == null)) {
            if (person.isEnemy()) {
                g.setColor(Colors.ENEMY);
            } else {
                g.setColor(Colors.PLAYER);
            }
            g.fillOval((colX + 1) * SQUARE_SPACING,(rowY + 1) * SQUARE_SPACING, SQUARE_HEIGHT, SQUARE_HEIGHT);
            person.setLocation((colX + 1) * SQUARE_SPACING,(rowY + 1) * SQUARE_SPACING);
            g.setColor(Color.BLACK);
            g.drawString(person.getCharacterCode(),
                    (colX + 1) * SQUARE_SPACING + (SQUARE_HEIGHT / 2) - HORIZ_TEXT_ADJUSTMENT,
                    (rowY + 1) * SQUARE_SPACING + (SQUARE_HEIGHT / 2) + VERT_TEXT_ADJUSTMENT);
        }
    }

    private void drawWall(int i, int j, Graphics g, SquareWall wall) {
        g.setColor(Colors.WALL);
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

    private void drawSquare(int rowY, int colX, Graphics g, SquareWall wall) {
        g.setColor(Colors.SQUARE);
        g.fillRect((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING, SQUARE_HEIGHT, SQUARE_HEIGHT);
        wall.setLocation((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING);
    }

    public int getSelectedSquare() {
        return selectedSquare;
    }
}
