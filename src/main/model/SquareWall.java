package model;

import java.util.ArrayList;

//represents a single square with where each wall is represented
public class SquareWall {

    private boolean leftWall;
    private boolean rightWall;
    private boolean upperWall;
    private boolean lowerWall;

    //EFFECTS: creates a square with no walls
    public SquareWall() {
        leftWall = false;
        rightWall = false;
        upperWall = false;
        lowerWall = false;
    }

    //EFFECTS: creates a square with input walls
    public SquareWall(boolean lfw, boolean rw, boolean uw, boolean low) {
        leftWall = lfw;
        rightWall = rw;
        upperWall = uw;
        lowerWall = low;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public boolean isUpperWall() {
        return upperWall;
    }

    public boolean isLowerWall() {
        return lowerWall;
    }
}
