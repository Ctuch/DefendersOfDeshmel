package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//Represents a set of wall configuration sets, and randomly generates one for the board when instantiated
public class SquareWallConfigs {

    private ArrayList<SquareWall> walls;

    public SquareWallConfigs() {
        Random random = new Random();
        walls = generateRandomWallSet(random.nextInt(1) + 1);
    }

    //EFFECTS: randomly selects on of the wall configs
    public ArrayList<SquareWall> generateRandomWallSet(int i) {
        switch (i) {
            default:
                return generateWallSetOne();
        }
    }

    //EFFECTS: generates a squareWall for each square of the board, configuration 1
    private static ArrayList<SquareWall> generateWallSetOne() {
        return new ArrayList<>(Arrays.asList(new SquareWall(true, false, true, false), //0
                new SquareWall(),  new SquareWall(), //1 //2
                new SquareWall(), new SquareWall(),  //3 //4
                new SquareWall(), //5
                new SquareWall(), //6
                new SquareWall(false, true, false, false), //7
                new SquareWall(true, false, false, true), //8
                new SquareWall(false, true, false, false), //9
                new SquareWall(), //10
                new SquareWall(false, true, false, false), //11
                new SquareWall(true, false, false, false), //12
                new SquareWall(false, false, true, false), //13
                new SquareWall(false, true, false, false), //14
                new SquareWall(false, true, false, false), //15
                new SquareWall(true, false, false, true), //16
                new SquareWall(false, true, false, false), //17
                new SquareWall(true, false, false, false), //18
                new SquareWall(false, true, false, false), //19
                new SquareWall(), //20
                new SquareWall(false, true, true, false), //21
                new SquareWall(true, true, false, false), //22
                new SquareWall(true, false, false, true), //23
                new SquareWall())); //24
    }

    public ArrayList<SquareWall> getWalls() {
        return walls;
    }
}
