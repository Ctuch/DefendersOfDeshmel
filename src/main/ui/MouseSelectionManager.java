package ui;

import model.Person;

import java.util.ArrayList;

public class MouseSelectionManager {

    //TODO: exactly. the same as personPanel
    public boolean isInSpace(int mouseX, int mouseY, int locationX, int locationY) {
        int differenceX = mouseX - locationX;
        int differenceY = mouseY - locationY;
        int maxDifference = BoardPanel.SQUARE_HEIGHT;
        return differenceX <= maxDifference && differenceX >= 0 && differenceY <= maxDifference && differenceY >= 0;
    }

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
