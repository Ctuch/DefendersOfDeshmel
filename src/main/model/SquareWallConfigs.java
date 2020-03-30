package model;

import java.util.*;

//Represents a set of wall configuration sets, and randomly generates one for the board when instantiated
public class SquareWallConfigs {

    private Map<Integer, SquareWall> walls;
    private static int wallSetNum;

    //EFFECTS: generates a random wall set
    public SquareWallConfigs() {
        Random random = new Random();
        walls = generateRandomWallSet(random.nextInt(1) + 1);
    }

    //EFFECTS: randomly selects on of the wall configs
    public static Map<Integer, SquareWall> generateRandomWallSet(int i) {
        wallSetNum = i;
        switch (i) {
            case 1:
                return generateWallSetOne();
            default:
                return generateWallSetEmpty();
        }
    }

    //EFFECTS: generates a squareWall for each square of the board, configuration 1
    public static Map<Integer, SquareWall> generateWallSetOne() {
        Map<Integer, SquareWall> wallMap = new HashMap<>();
        wallMap.put(0, new SquareWall(true, false, true, false));
        wallMap.put(7, new SquareWall(false, true, false, false));
        wallMap.put(8, new SquareWall(true, true, false, true));
        wallMap.put(9, new SquareWall(true, true, false, false));
        wallMap.put(11, new SquareWall(false, true, false, false));
        wallMap.put(12, new SquareWall(true, false, false, false));
        wallMap.put(13, new SquareWall(false, false, true, false));
        wallMap.put(14, new SquareWall(false, true, false, false));
        wallMap.put(15, new SquareWall(false, true, false, false));
        wallMap.put(16, new SquareWall(true, false, false, true));
        wallMap.put(17, new SquareWall(false, true, false, false));
        wallMap.put(18, new SquareWall(true, false, false, false));
        wallMap.put(19, new SquareWall(false, true, false, false));
        wallMap.put(21, new SquareWall(false, true, true, false));
        wallMap.put(22, new SquareWall(true, true, false, false));
        wallMap.put(23, new SquareWall(true, false, false, true));

        List<Integer> emptyKeys = Arrays.asList(1, 2, 3, 4, 5, 6, 10, 20, 24);
        fillEmptyWalls(emptyKeys, wallMap);
        return wallMap;
    }

    //MODIFIES: wallMap
    //EFFECTS: adds empty walls for the wall set given a list of empty positions
    private static void fillEmptyWalls(List<Integer> emptyKeys, Map<Integer, SquareWall> wallMap) {
        for (Integer key : emptyKeys) {
            wallMap.put(key, new SquareWall());
        }
    }

    //EFFECTS: generates a squareWall for each square of the board, configuration empty (no walls)
    public static Map<Integer, SquareWall> generateWallSetEmpty() {
        Map<Integer, SquareWall> wallSet = new HashMap<>();;
        for (int i = 0; i < 25; i++) {
            wallSet.put(i, new SquareWall());
        }
        return wallSet;
    }

    public Map<Integer, SquareWall> getWalls() {
        return walls;
    }

    public static int getWallSetNum() {
        return wallSetNum;
    }

    public void setWalls(Map<Integer, SquareWall> walls) {
        this.walls = walls;
    }

}
