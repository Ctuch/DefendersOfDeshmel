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
    private int selectedSquare1st = -1;
    private int selectedSquare2nd = -1;
    private MouseSelectionManager mouseSelection;

    public BoardPanel(Board board) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.GRAY);
        this.board = board;
        mouseSelection = new MouseSelectionManager();
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

    public void updateSelectedSquare(int mouseX, int mouseY) {
        //TODO: abstract this out since copying and pasting functionality from personPanel - selector class?
        ArrayList<SquareWall> walls = board.getWallConfig();
        for (int i = 0; i < walls.size(); i++) {
            if (mouseSelection.isInSpace(mouseX, mouseY, walls.get(i).getLocationX(), walls.get(i).getLocationY())) {
                if (selectedSquare1st != -1 || selectedSquare2nd != -1) {
                    selectedSquare1st = selectedSquare2nd;
                }
                selectedSquare2nd = i;
                DefenderOfDeshmelDisplay.setSelectedPlayer(board.getBoard().get(selectedSquare2nd));
                System.out.println("selected square 1: " + selectedSquare1st);
                System.out.println("selected square 2: " + selectedSquare2nd);
                return;
            }
        }
        System.out.println("selected square 1: " + selectedSquare1st);
        System.out.println("selected square 2: " + selectedSquare2nd);
        selectedSquare1st = -1;
        selectedSquare2nd = -1;
        DefenderOfDeshmelDisplay.setSelectedPlayer(null);
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
            System.out.println("my new location: " + person.getName());
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

    public int getSelectedSquare1st() {
        return selectedSquare1st;
    }

    public int getSelectedSquare2nd() {
        return selectedSquare2nd;
    }
}
