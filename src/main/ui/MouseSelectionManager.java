package ui;

import model.Person;

import java.util.ArrayList;

//class for determining result of mouse click
public class MouseSelectionManager {

    //EFFECTS: returns true if the mouse clicks within the square of the person
    public boolean isInSpace(int mouseX, int mouseY, int locationX, int locationY) {
        int differenceX = mouseX - locationX;
        int differenceY = mouseY - locationY;
        int maxDifference = BoardPanel.SQUARE_HEIGHT;
        return differenceX <= maxDifference && differenceX >= 0 && differenceY <= maxDifference && differenceY >= 0;
    }

    //EEFECTS: returns the person who is in the selected square or null if no player there
    public Person updateSelectedPlayer(int mouseX, int mouseY, ArrayList<Person> players) {
        for (Person player : players) {
            if (isInSpace(mouseX, mouseY, player.getLocationX(), player.getLocationY())) {
                System.out.println("selected player " + player.getName());
                return player;
            }
        }
        return null;
    }
}
