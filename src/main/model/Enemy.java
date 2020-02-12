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
        int rowDif = rowPosThis - rowPosPerson;
        int colDif = colPosThis - colPosPerson;
        SquareWall wallSet = board.getWallConfig().get(boardState.indexOf(this));
        ArrayList<Action> viableDirections = getViableDirectionsToMove(wallSet);

        if (Math.abs(rowDif) > Math.abs(colDif)) {
            if (rowDif > 0 && viableDirections.contains(Action.MOVE_UP)) {
                return Action.MOVE_UP;
            } else if (rowDif < 0 && viableDirections.contains(Action.MOVE_DOWN)) {
                return Action.MOVE_DOWN;
            }
        } else {
            if (colDif > 0 && viableDirections.contains(Action.MOVE_LEFT)) {
                return Action.MOVE_LEFT;
            } else if (colDif < 0 && viableDirections.contains(Action.MOVE_RIGHT)) {
                return Action.MOVE_RIGHT;
            }
        }
        Random random = new Random();
        return viableDirections.get(random.nextInt(viableDirections.size()));
    }

    //REQUIRES: wallSet not null
    //EFFECTS: generates a list of viable directions to move in
    public ArrayList<Action> getViableDirectionsToMove(SquareWall wallSet) {
        //TODO: BUG: NOT A VIABLE DIRECTION IF ENEMY IN NEXT SQUARE
        //TODO: BUG DOES NOT CHECK BORDER CONDITIONS
        ArrayList<Action> viableDirections = new ArrayList<>();
        if (!wallSet.isLeftWall()) {
            viableDirections.add(Action.MOVE_LEFT);
        }
        if (!wallSet.isRightWall()) {
            viableDirections.add(Action.MOVE_RIGHT);
        }
        if (!wallSet.isUpperWall()) {
            viableDirections.add(Action.MOVE_UP);
        }
        if (!wallSet.isLowerWall()) {
            viableDirections.add(Action.MOVE_DOWN);
        }
        return viableDirections;
    }
}
