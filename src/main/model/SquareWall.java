package model;

//represents a single square with where each wall is represented
public class SquareWall {

    private boolean leftWall;
    private boolean rightWall;
    private boolean upperWall;
    private boolean lowerWall;

    public SquareWall(boolean lfw, boolean rw, boolean uw, boolean low) {
        leftWall = lfw;
        rightWall = rw;
        upperWall = uw;
        lowerWall = low;
    }

}
