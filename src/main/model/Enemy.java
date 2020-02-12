package model;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Person {

    public Enemy(String name) {
        super(name);
        this.isEnemy = true;
    }

    public Action decideAction(Board board) {
        if (isAvailable()) {
            return Action.ADD;
        }

        if (canAttackPerson(board) != null) {
            return Action.ATTACK;
        }

        return findClosestPerson(board);
    }

    public Person canAttackPerson(Board board) {
        ArrayList<Person> boardState = board.getBoard();
        for (Person person : boardState) {
            if (person != null && board.isInWeaponRange(this, person) && !person.isEnemy()) {
                return person;
            }
        }
        return null;
    }

    //REQUIRES: enemy is on board
    public Action findClosestPerson(Board board) {
        ArrayList<Person> boardState = board.getBoard();
        int rowPosThis = board.getRowNumber(this);
        int colPosThis = board.getColumnNumber(this);

        int shortestDistance = 10;
        int indexOfShortestDistance = -1;

        for (int i = 0; i < boardState.size(); i++) {
            int rowPosPerson = board.getRowNumber(boardState.get(i));
            int colPosPerson = board.getColumnNumber(boardState.get(i));
            int distance = Math.abs(rowPosPerson - rowPosThis) + Math.abs(colPosPerson - colPosThis);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                indexOfShortestDistance = i;
            }
        }
        //if no targets on board, move to the middle
        if (indexOfShortestDistance == -1) {
            return moveTowardTarget(board, 12, rowPosThis, colPosThis);
        }
        return moveTowardTarget(board, indexOfShortestDistance, rowPosThis, colPosThis);
    }

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
            } else if (colDif < 0 && viableDirections.contains(Action.MOVE_DOWN)) {
                return Action.MOVE_DOWN;
            }
        }
        Random random = new Random();
        return viableDirections.get(random.nextInt(viableDirections.size()));
    }

    private ArrayList<Action> getViableDirectionsToMove(SquareWall wallSet) {
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
