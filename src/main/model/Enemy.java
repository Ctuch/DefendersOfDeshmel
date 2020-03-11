package model;

import java.util.ArrayList;
import java.util.Random;

//subclass of person to allow the "AI" control of the enemies
public class Enemy extends Person {

    //creates an enemy as a person with an enemy designation
    public Enemy(String name) {
        super(name);
        this.isEnemy = true;
    }

    //REQUIRES: board is not null
    //EFFECTS: determines what action the enemy will take with the following criteria:
    //         if the enemy is not on the board, select the ADD action
    //         if the enemy is in attack range of any person, attacks that person
    //         otherwise, the enemy moves toward the nearest person (or middle if no persons on board)
    public Action decideAction(Board board) {
        if (isAvailable()) {
            return Action.ADD;
        }

        if (canAttackPerson(board) != null) {
            return Action.ATTACK;
        }

        return findClosestPerson(board);
    }

    //REQUIRES: board is not null
    //EFFECTS: selects the first person that is in weapon range to attack, or null if none are in range
    public Person canAttackPerson(Board board) {
        ArrayList<Person> boardState = board.getBoard();
        for (Person person : boardState) {
            if (person != null && board.isInWeaponRange(this, person) && !person.isEnemy()) {
                return person;
            }
        }
        return null;
    }

    //REQUIRES: enemy is on board, board is not null
    //EFFECTS: finds the closest person to this and moves toward it, or moves to the middle if no person on board
    public Action findClosestPerson(Board board) {
        ArrayList<Person> boardState = board.getBoard();
        int rowPosThis = board.getRowNumber(this);
        int colPosThis = board.getColumnNumber(this);

        int shortestDistance = 10;
        int indexOfShortestDistance = -1;

        for (int i = 0; i < boardState.size(); i++) {
            if (boardState.get(i) != null && !boardState.get(i).isEnemy()) {
                int rowPosPerson = board.getRowNumber(boardState.get(i));
                int colPosPerson = board.getColumnNumber(boardState.get(i));
                int distance = Math.abs(rowPosPerson - rowPosThis) + Math.abs(colPosPerson - colPosThis);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    indexOfShortestDistance = i;
                }
            }
        }
        //if no targets on board, move to the middle
        if (indexOfShortestDistance == -1) {
            return moveTowardTarget(board, 12, rowPosThis, colPosThis);
        }
        return moveTowardTarget(board, indexOfShortestDistance, rowPosThis, colPosThis);
    }

    //REQUIRES: board not null, 0 <= index < 25
    //EFFECTS: moves enemy toward the given index in any viable direction
    public Action moveTowardTarget(Board board, int index, int rowPosThis, int colPosThis) {
        ArrayList<Person> boardState = board.getBoard();

        int rowPosPerson = board.getRowNumber(boardState.get(index));
        int colPosPerson = board.getColumnNumber(boardState.get(index));
        SquareWall wallSet = board.getWallConfig().get(boardState.indexOf(this));
        ArrayList<Action> viableDirections = getViableDirectionsToMove(wallSet, rowPosThis, colPosThis, boardState);
        ArrayList<Action> desiredDirections = new ArrayList<>();
        if (colPosThis - colPosPerson > 0) {
            desiredDirections.add(Action.MOVE_LEFT);
        } else if (colPosThis - colPosPerson < 0) {
            desiredDirections.add(Action.MOVE_RIGHT);
        }
        if (rowPosThis - rowPosPerson > 0) {
            desiredDirections.add(Action.MOVE_UP);
        } else if (rowPosThis - rowPosPerson < 0) {
            desiredDirections.add(Action.MOVE_DOWN);
        }
        for (Action direction : desiredDirections) {
            if (viableDirections.contains(direction)) {
                return direction;
            }
        }
        Random random = new Random();
        return viableDirections.get(random.nextInt(viableDirections.size()));
    }

    //REQUIRES: wallSet not null
    //EFFECTS: generates a list of viable directions to move in
    public ArrayList<Action> getViableDirectionsToMove(SquareWall wallSet, int row, int col, ArrayList<Person> bs) {
        ArrayList<Action> viableDirections = new ArrayList<>();
        if (!wallSet.isLeftWall() && col != 0 && bs.get(row * 5 + col - 1) == null) {
            viableDirections.add(Action.MOVE_LEFT);
        }
        if (!wallSet.isRightWall() && col != 4 && bs.get(row * 5 + col + 1) == null) {
            viableDirections.add(Action.MOVE_RIGHT);
        }
        if (!wallSet.isUpperWall() && row != 0 && bs.get((row - 1) * 5 + col) == null) {
            viableDirections.add(Action.MOVE_UP);
        }
        if (!wallSet.isLowerWall() && row != 4 && bs.get((row + 1) * 5 + col) == null) {
            viableDirections.add(Action.MOVE_DOWN);
        }
        return viableDirections;
    }

    //REQUIRES: i = 3, 6, or 10
    //MODIFIES: this
    //EFFECTS: clears out all enemies, and adds i new enemies to enemies
    public static void addEnemies(int i, ArrayList<Enemy> enemies) {
        enemies.clear();
        if (i >= 3) {
            enemies.add(new Enemy("Foot Soldier"));
            enemies.add(new Enemy("Ranged Shooter"));
            //add a third enemy
            //TODO: design 8 more enemies
        }
        if (i >= 6) {
            //add 3 more enemies
        }
        if (i == 10) {
            //add 4 more enemies
        }
    }

    //MODIFIES: this
    //EFFECTS: removes dead enemies from the board and enemy list
    public static void removeDeadEnemies(Board board, ArrayList<Enemy> enemies) {
        ArrayList<Person> boardState = board.getBoard();
        for (int i = 0; i < 25; i++) {
            if (boardState.get(i) != null && boardState.get(i).isDead()) {
                boardState.set(i, null);
            }
        }
        enemies.removeIf(Person::isDead);
    }
}
