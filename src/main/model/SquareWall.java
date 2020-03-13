package model;

//represents a single square with where each wall is represented and a location of the upper left corner
public class SquareWall {

    private boolean leftWall;
    private boolean rightWall;
    private boolean upperWall;
    private boolean lowerWall;
    private int locationX;
    private int locationY;

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

    public int getLocationX() {
        return locationX;
    }

    public void setLocation(int locationX, int locationY) {
        setLocationX(locationX);
        setLocationY(locationY);
    }

    private void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    private void setLocationY(int locationY) {
        this.locationY = locationY;
    }

}
