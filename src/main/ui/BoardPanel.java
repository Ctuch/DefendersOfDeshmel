package ui;

import model.Board;
import model.Person;
import model.SquareWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//panel for displaying characters, walls and board squares on board
public class BoardPanel extends JPanel {
//TODO: create a parent class that has an implementation for drawing the characters (board panel, offboard panel?)
    protected static final int SQUARE_SPACING = 60;
    protected static final int SQUARE_HEIGHT = 50;
    protected static final int VERT_TEXT_ADJUSTMENT = 5;
    protected static final int HORIZ_TEXT_ADJUSTMENT = 7;
    protected static final int BOARD_WIDTH = SQUARE_SPACING * 7;
    protected static final int BOARD_HEIGHT = SQUARE_SPACING * 7;
    protected static final int INVALID = -1;

    private Board board;
    private int selectedSquare1st = INVALID;
    private int selectedSquare2nd = INVALID;
    private MouseSelectionManager mouseSelection;

    //EFFECTS: sets the panel layout, initializes variables, and adds mouse control to the panel
    public BoardPanel(Board board) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.GRAY);
        this.board = board;
        mouseSelection = new MouseSelectionManager();
        addMouseControl();
    }

    //MODIFIES: display
    //EFFECTS: redraws the players, enemies, squares and walls onto the display
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

    //MODIFIES: this
    //EFFECTS: adds a mouse listener to the board that remembers the last 2 selected squares by the user
    private void addMouseControl() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                updateSelectedSquare(e.getX(), e.getY());
            }
        });
    }

    //MODIFIES: this, display (selectedPlayer)
    //EFFECTS: if there is a square where the mouse selects,
    //                  sets the second selected square to that value
    //                  the display selected player to that player
    //                  and the first selected player to the previously selected square (2nd selected)
    //         if there is no square where the mouse selects,
    //                  sets the selected squares to -1
    //                  sets the selected player on the display to null
    public void updateSelectedSquare(int mouseX, int mouseY) {
        ArrayList<SquareWall> walls = board.getWallConfig();
        for (int i = 0; i < walls.size(); i++) {
            if (mouseSelection.isInSpace(mouseX, mouseY, walls.get(i).getLocationX(), walls.get(i).getLocationY())) {
                if (selectedSquare1st != INVALID || selectedSquare2nd != INVALID) {
                    selectedSquare1st = selectedSquare2nd;
                }
                selectedSquare2nd = i;
                DefenderOfDeshmelDisplay.setSelectedPlayer(board.getBoard().get(selectedSquare2nd));
                return;
            }
        }
        selectedSquare1st = INVALID;
        selectedSquare2nd = INVALID;
        DefenderOfDeshmelDisplay.setSelectedPlayer(null);
    }

    //MODIFIES: this
    //EFFECTS: draws the enemies and players onto the board
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

    //MODIFIES: this
    //EFFECTS: draws the walls onto the board
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

    //MODIFIES: this
    //EFFECTS: draws the right walls onto the board
    private void drawRightWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING + SQUARE_HEIGHT, (rowY + 1) * SQUARE_SPACING,
                SQUARE_SPACING - SQUARE_HEIGHT, SQUARE_HEIGHT);
    }

    //MODIFIES: this
    //EFFECTS: draws the left walls onto the board
    private void drawLeftWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING - (SQUARE_SPACING - SQUARE_HEIGHT), (rowY + 1) * SQUARE_SPACING,
                SQUARE_SPACING - SQUARE_HEIGHT, SQUARE_HEIGHT);
    }

    //MODIFIES: this
    //EFFECTS: draws the lower walls onto the board
    private void drawLowerWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING + SQUARE_HEIGHT,
                SQUARE_HEIGHT, SQUARE_SPACING - SQUARE_HEIGHT);
    }

    //MODIFIES: this
    //EFFECTS: draws the upper walls onto the board
    private void drawUpperWall(int rowY, int colX, Graphics g) {
        g.fillRect((colX + 1) * SQUARE_SPACING, (rowY + 1) * SQUARE_SPACING - (SQUARE_SPACING - SQUARE_HEIGHT),
                SQUARE_HEIGHT, SQUARE_SPACING - SQUARE_HEIGHT);
    }

    //MODIFIES: this
    //EFFECTS: draws the squares onto the board
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
