package model;

import model.exceptions.*;

import java.util.ArrayList;
import java.util.Random;

//subclass of person to allow the "AI" control of the enemies
public class Enemy extends Person {

    //EFFECTS: creates an enemy as a person with an enemy designation
    public Enemy(String name) {
        super(name);
        this.isEnemy = true;
    }

    //EFFECTS: determines what action the enemy will take with the following criteria:
    //         if the enemy is not on the board, select the ADD action
    //         if the enemy is in attack range of any person, attacks that person
    //         otherwise, the enemy moves toward the nearest person (or middle if no persons on board)
    public Action decideAction(Board board) throws IndexNotInBoundsException,
            PersonNotOnBoardException, NoViableDirectionException {

        if (isAvailable()) {
            return Action.ADD;
        }

        if (canAttackPerson(board) != null) {
            return Action.ATTACK;
        }

        return findClosestPerson(board);
    }

    //EFFECTS: selects the first person that is in weapon range to attack, or null if none are in range
    //         note: board is passed in from methods that check if it is null always
    public Person canAttackPerson(Board board) {

        ArrayList<Person> boardState = board.getBoard();
        for (Person person : boardState) {
            if (person != null && board.isInWeaponRange(this, person) && !person.isEnemy()) {
                return person;
            }
        }
        return null;
    }

    //EFFECTS: finds the closest person to this and moves toward it, or moves to the middle if no person on board
    public Action findClosestPerson(Board board) throws PersonNotOnBoardException,
            IndexNotInBoundsException, NoViableDirectionException {

        if (this.isAvailable()) {
            throw new PersonNotOnBoardException();
        }

        int rowPosThis = board.getRowNumber(this);
        int colPosThis = board.getColumnNumber(this);

        int shortestDistance = 10;
        int indexOfShortestDistance = -1;

        for (int i = 0; i < board.getBoard().size(); i++) {
            if (board.getBoard().get(i) != null && !board.getBoard().get(i).isEnemy()) {
                int rowPosPerson = board.getRowNumber(board.getBoard().get(i));
                int colPosPerson = board.getColumnNumber(board.getBoard().get(i));
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

    //EFFECTS: moves enemy toward the given index in any viable direction
    public Action moveTowardTarget(Board board, int index, int rowPosThis, int colPosThis)
            throws IndexNotInBoundsException, NoViableDirectionException {

        checkInBounds(index, 24, 0);
        ArrayList<Person> boardState = board.getBoard();

        int rowPosPerson = board.getRowNumber(boardState.get(index));
        int colPosPerson = board.getColumnNumber(boardState.get(index));
        SquareWall wallSet = board.getWallConfig().get(boardState.indexOf(this));
        ArrayList<Action> viableDirections = getViableDirectionsToMove(wallSet, rowPosThis, colPosThis, boardState);
        ArrayList<Action> desiredDirections = findDesiredDirections(colPosThis, colPosPerson, rowPosThis, rowPosPerson);

        for (Action direction : desiredDirections) {
            if (viableDirections.contains(direction)) {
                return direction;
            }
        }
        Random random = new Random();
        if (viableDirections.isEmpty()) {
            throw new NoViableDirectionException();
        }
        return viableDirections.get(random.nextInt(viableDirections.size()));
    }

    //EFFECTS: creates a list of directions that would move this toward the person
    private ArrayList<Action> findDesiredDirections(int colPosThis, int colPosPerson, int rowPosThis, int rowPosPers) {
        ArrayList<Action> desiredDirections = new ArrayList<>();
        if (colPosThis - colPosPerson > 0) {
            desiredDirections.add(Action.MOVE_LEFT);
        } else if (colPosThis - colPosPerson < 0) {
            desiredDirections.add(Action.MOVE_RIGHT);
        }
        if (rowPosThis - rowPosPers > 0) {
            desiredDirections.add(Action.MOVE_UP);
        } else if (rowPosThis - rowPosPers < 0) {
            desiredDirections.add(Action.MOVE_DOWN);
        }
        return desiredDirections;
    }

    //EFFECTS: throws IndexOutOfBoundsException if index is not between bounds
    private void checkInBounds(int index, int upperBound, int lowerBound) throws IndexNotInBoundsException {
        if (lowerBound > index || index > upperBound) {
            throw new IndexNotInBoundsException();
        }
    }

    //EFFECTS: generates a list of viable directions to move in, adding those that dont move through walls
    //         and not into other people
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

    //MODIFIES: this
    //EFFECTS: clears out all enemies, and adds new enemies to enemies based on difficulty
    public static void addEnemies(Difficulty difficulty, ArrayList<Enemy> enemies) {
        enemies.clear();
        enemies.add(new Enemy("Foot Soldier"));
        enemies.add(new Enemy("Ranged Shooter"));
        if (difficulty == Difficulty.MEDIUM || difficulty == Difficulty.HARD) {
            enemies.add(new Enemy("Sharp Shooter"));
        }
        if (difficulty == Difficulty.HARD) {
            enemies.add(new Enemy("Warped Knight"));
        }
    }
}
