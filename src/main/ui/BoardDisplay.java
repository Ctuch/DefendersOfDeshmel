package ui;

import model.Board;
import model.Person;
import model.SquareWall;

public class BoardDisplay {
    private Board board;

    public BoardDisplay(Board board) {
        this.board = board;
    }

    //EFFECTS: displays current state of board to user
    public void displayBoard() {
        String bottomBorder = " ";

        for (int i = 0; i < 5; i++) {
            StringBuilder topBorder = new StringBuilder(" ");
            StringBuilder textRow = new StringBuilder();
            StringBuilder noTextRow = new StringBuilder();
            SquareWall wall = null;

            for (int j = 0; j < 5; j++) {
                wall = board.getWallConfig().get(5 * i + j);
                Person person = board.getBoard().get(5 * i + j);

                topBorder.append(createUpperBorder(wall));

                if (i == 4) {
                    bottomBorder += createLowerBorder(wall);
                }
                String[] middleRows = createLeftBorder(wall, person);
                textRow.append(middleRows[0]);
                noTextRow.append(middleRows[1]);
            }
            String[] finalCharacters = createRightMostCharacter(wall);
            textRow.append(finalCharacters[0]);
            noTextRow.append(finalCharacters[1]);

            System.out.println(topBorder + "\n" + textRow + "\n" + noTextRow);
        }
        System.out.println(bottomBorder);
    }

    //EFFECTS: left side of each square and the character codes depending on whether there is a wall there or not
    //         and if the square is full
    private String[] createLeftBorder(SquareWall wall, Person person) {
        String textRow = "";
        String noTextRow = "";
        if (wall.isLeftWall()) {
            if (person != null) {
                textRow += "#  " + person.getCharacterCode() + "  ";
            } else {
                textRow += "#      ";
            }
            noTextRow += "#      ";
        } else {
            if (person != null) {
                textRow += "|  " + person.getCharacterCode() + "  ";
            } else {
                textRow += "|      ";
            }
            noTextRow += "|      ";
        }
        return new String[]{textRow, noTextRow};
    }

    //EFFECTS: adds the final border row depending on whether there is a wall there or not
    private String createLowerBorder(SquareWall wall) {
        if (wall.isLowerWall()) {
            return "###### ";
        } else {
            return "------ ";
        }
    }

    //EFFECTS: adds the border row above the square depending on whether there is a wall there or not
    private String createUpperBorder(SquareWall wall) {
        if (wall.isUpperWall()) {
            return "###### ";
        } else {
            return "------ ";
        }
    }

    //EFFECTS: adds the final character of each row depending on whether there is a wall there or not
    private String[] createRightMostCharacter(SquareWall wall) {
        String textRow;
        String noTextRow;
        if (wall.isRightWall()) {
            textRow = "#";
            noTextRow = "#";
        } else {
            textRow = "|";
            noTextRow = "|";
        }
        return new String[]{textRow, noTextRow};
    }
}
