package model;


import model.exceptions.IndexNotInBoundsException;
import model.exceptions.NoViableDirectionException;
import model.exceptions.PersonNotOnBoardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    Enemy enemyFS;
    Enemy enemyRS;
    Person personIce;
    Person personFire;
    Board board;
    ArrayList<Enemy> enemies;

    @BeforeEach
    public void runBefore() {
        enemyFS = new Enemy("Foot Soldier");
        enemyRS = new Enemy("Ranged Shooter");
        personIce = new Person("Ice Sorcerer");
        personFire = new Person("Fire Sorceress");
        board = new Board();
        board.setWallConfig(SquareWallConfigs.generateWallSetEmpty());
        enemies = new ArrayList<>();
    }

    @Test
    public void testCanAttackPersonOnlyEnemyInRange() {
        board.addCharacter(0, enemyFS);
        board.addCharacter(5, enemyRS);
        board.addCharacter(13, personFire);

        Person nullPerson = enemyFS.canAttackPerson(board);
        Person nullPerson2 = enemyRS.canAttackPerson(board);
        assertNull(nullPerson);
        assertNull(nullPerson2);
    }

    @Test
    public void testCanAttackPersonInRange() {
        board.addCharacter(10, enemyFS);
        board.addCharacter(5, personIce);
        board.addCharacter(8, enemyRS);
        board.addCharacter(3, personFire);

        Person person = enemyFS.canAttackPerson(board);
        Person person2 = enemyRS.canAttackPerson(board);
        assertEquals(person, personIce);
        assertEquals(person2, personFire);
    }

    @Test
    public void testCanAttackPersonNotInRange() {
        board.addCharacter(10, enemyFS);
        board.addCharacter(0, personIce);
        board.addCharacter(9, enemyRS);
        board.addCharacter(3, personFire);

        Person nullPerson = enemyFS.canAttackPerson(board);
        Person nullPerson2 = enemyRS.canAttackPerson(board);
        assertNull(nullPerson);
        assertNull(nullPerson2);
    }

    @Test
    public void testGetViableDirectionsAllWallCondition() {
        board.addCharacter(12, enemyFS);
        SquareWall wall = new SquareWall();
        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_UP);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsLeftRightWallCondition() {
        board.addCharacter(12, enemyFS);
        SquareWall wall = new SquareWall(false, false, true, true);
        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_RIGHT);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsDownLeftWallCondition() {
        board.addCharacter(12, enemyFS);
        SquareWall wall = new SquareWall(false, true, true, false);
        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsUpRightDownWallCondition() {
        board.addCharacter(12, enemyFS);
        SquareWall wall = new SquareWall(true, false, false, false);
        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_UP);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsUpWallCondition() {
        board.addCharacter(12, enemyFS);
        SquareWall wall = new SquareWall(true, true, false, true);
        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_UP);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotLeftBoundaryCondition() {
        board.addCharacter(10, enemyFS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_UP);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 0, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotRightBoundaryCondition() {
        board.addCharacter(14, enemyFS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_UP);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 4, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotUpBoundaryCondition() {
        board.addCharacter(3, enemyFS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 0, 3, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotDownBoundaryCondition() {
        board.addCharacter(22, enemyFS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_UP);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 4, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotLeftOccupied() {
        board.addCharacter(22, enemyFS);
        board.addCharacter(21, enemyRS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_UP);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 4, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotRightOccupied() {
        board.addCharacter(13, enemyFS);
        board.addCharacter(14, enemyRS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_UP);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 2, 3, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotUpOccupied() {
        board.addCharacter(17, enemyFS);
        board.addCharacter(12, enemyRS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_RIGHT);
        viableDirections.add(Action.MOVE_DOWN);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 3, 2, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testGetViableDirectionsNotDownOccupied() {
        board.addCharacter(9, enemyFS);
        board.addCharacter(14, enemyRS);
        SquareWall wall = new SquareWall();

        ArrayList<Action> viableDirections = new ArrayList<>();
        viableDirections.add(Action.MOVE_LEFT);
        viableDirections.add(Action.MOVE_UP);

        ArrayList<Action> viableDirFromMethod = enemyFS.getViableDirectionsToMove(wall, 1, 4, board.getBoard());
        for (int i = 0; i < viableDirections.size(); i++) {
            assertEquals(viableDirections.get(i), viableDirFromMethod.get(i));
        }
    }

    @Test
    public void testMoveTowardTargetUp() {
        board.addCharacter(12, enemyFS);
        board.addCharacter(2, personIce);
        try {
            assertEquals(enemyFS.moveTowardTarget(board, 2, 2, 2), Action.MOVE_UP);
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetDown() {
        board.addCharacter(2, enemyFS);
        board.addCharacter(12, personIce);
        try {
            assertEquals(enemyFS.moveTowardTarget(board, 12, 0, 2), Action.MOVE_DOWN);
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetLeft() {
        board.addCharacter(12, enemyFS);
        board.addCharacter(10, personIce);
        try {
            assertEquals(enemyFS.moveTowardTarget(board, 10, 2, 2), Action.MOVE_LEFT);
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetRight() {
        board.addCharacter(10, enemyFS);
        board.addCharacter(12, personIce);
        try {
            assertEquals(Action.MOVE_RIGHT, enemyFS.moveTowardTarget(board, 12, 2, 0));
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetRightColGreater() {
        board.addCharacter(10, enemyFS);
        board.addCharacter(17, personIce);
        try {
            assertEquals(Action.MOVE_RIGHT, enemyFS.moveTowardTarget(board, 17, 2, 0));
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetLeftColRowSame() {
        board.addCharacter(14, enemyFS);
        board.addCharacter(22, personIce);
        try {
            assertEquals(Action.MOVE_LEFT, enemyFS.moveTowardTarget(board, 22, 2, 4));
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetDownRowGreater() {
        board.addCharacter(3, enemyFS);
        board.addCharacter(22, personIce);
        try {
            assertEquals(Action.MOVE_LEFT, enemyFS.moveTowardTarget(board, 22, 0, 3));
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetOnlyOneViableDirectionAwayFromTarget() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(8, enemyFS);
        board.addCharacter(19, personIce);
        try {
            assertEquals(Action.MOVE_UP, enemyFS.moveTowardTarget(board, 19, 1, 3));
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetIndexNotValidLow() {
        board.addCharacter(8, enemyFS);
        board.addCharacter(19, personIce);
        try {
            enemyFS.moveTowardTarget(board, -7, 3, 2);
            fail();
        } catch (IndexNotInBoundsException e) {
            //good
        } catch (NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetIndexNotValidHighBorderCase() {
        board.addCharacter(2, enemyFS);
        board.addCharacter(1, personIce);
        try {
            enemyFS.moveTowardTarget(board, 25, 3, 2);
            fail();
        } catch (IndexNotInBoundsException e) {
            //good
        } catch (NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetIndexNotValidHigh() {
        board.addCharacter(3, enemyFS);
        board.addCharacter(21, personIce);
        try {
            enemyFS.moveTowardTarget(board, 100, 3, 2);
            fail();
        } catch (IndexNotInBoundsException e) {
            //good
        } catch (NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetIndexNotValidLowBorderCase() {
        board.addCharacter(15, enemyFS);
        board.addCharacter(6, personIce);
        try {
            enemyFS.moveTowardTarget(board, -1, 3, 2);
            fail();
        } catch (IndexNotInBoundsException e) {
            //good
        } catch (NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testMoveTowardTargetNoViableDirections() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(8, enemyFS);
        board.addCharacter(3, personIce);
        try {
            enemyFS.moveTowardTarget(board, 3, 1, 3);
            fail();
        } catch (IndexNotInBoundsException e) {
            fail();
        } catch (NoViableDirectionException e) {
            //good
        }
    }

    @Test
    public void testFindClosestPersonNoOneElseOnBoard() {
        board.addCharacter(14, enemyRS);
        try {
            assertEquals(Action.MOVE_LEFT, enemyRS.findClosestPerson(board));
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testFindClosestPersonUp() {
        board.addCharacter(14, enemyRS);
        board.addCharacter(13, enemyFS);
        board.addCharacter(3, personIce);
        board.addCharacter(20, personFire);
        try {
            assertEquals(Action.MOVE_UP, enemyRS.findClosestPerson(board));
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testFindClosestPersonDown() {
        board.addCharacter(13, enemyRS);
        board.addCharacter(14, enemyFS);
        board.addCharacter(24, personIce);
        board.addCharacter(0, personFire);
        Action findClosest = null;
        try {
            findClosest = enemyRS.findClosestPerson(board);
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
        assertEquals(Action.MOVE_DOWN, findClosest);
    }

    @Test
    public void testFindClosestPersonLeft() {
        board.addCharacter(13, enemyRS);
        board.addCharacter(14, enemyFS);
        board.addCharacter(6, personIce);
        board.addCharacter(24, personFire);
        try {
            assertEquals(Action.MOVE_LEFT, enemyRS.findClosestPerson(board));
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testFindClosestPersonRight() {
        board.addCharacter(6, enemyRS);
        board.addCharacter(14, enemyFS);
        board.addCharacter(4, personIce);
        board.addCharacter(18, personFire);
        try {
            assertEquals(Action.MOVE_RIGHT, enemyRS.findClosestPerson(board));
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testFindClosestPersonRightUpOnlyRightViable() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(13, enemyRS);
        board.addCharacter(4, personIce);
        try {
            assertEquals(Action.MOVE_RIGHT, enemyRS.findClosestPerson(board));
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testFindClosestPersonRightDownOnlyDownViable() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(11, enemyRS);
        board.addCharacter(22, personIce);
        try {
            assertEquals(Action.MOVE_DOWN, enemyRS.findClosestPerson(board));
        } catch (PersonNotOnBoardException | IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testFindClosestPersonEnemyNotOnBoard() {
        board.addCharacter(22, personIce);
        try {
            enemyRS.findClosestPerson(board);
            fail();
        } catch (PersonNotOnBoardException e) {
            //good
        } catch (IndexNotInBoundsException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testDecideActionNotOnBoard() {
        try {
            assertEquals(Action.ADD, enemyRS.decideAction(board));
            assertEquals(Action.ADD, enemyFS.decideAction(board));
        } catch (IndexNotInBoundsException | PersonNotOnBoardException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testDecideActionAttack() {
        board.addCharacter(12, enemyFS);
        board.addCharacter(17, personFire);
        try {
        assertEquals(Action.ATTACK, enemyFS.decideAction(board));
        } catch (IndexNotInBoundsException | PersonNotOnBoardException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testDecideActionMove() {
        board.addCharacter(12, enemyFS);
        board.addCharacter(18, personFire);
        try {
        assertEquals(Action.MOVE_RIGHT, enemyFS.decideAction(board));
        } catch (IndexNotInBoundsException | PersonNotOnBoardException | NoViableDirectionException e) {
            fail();
        }
    }

    @Test
    public void testAddEnemies() {
        Enemy fireDecoy = new Enemy("Fire Sorceress");
        enemies.add(fireDecoy);
        Enemy.addEnemies(Difficulty.EASY, enemies);
        assertEquals(enemyFS, enemies.get(0));
        assertEquals(enemyRS, enemies.get(1));
        assertEquals(enemies.size(), 2);
        assertFalse(enemies.contains(fireDecoy));

        Enemy.addEnemies(Difficulty.MEDIUM, enemies);
        assertEquals(enemies.size(), 3);
        assertEquals(new Enemy("Sharp Shooter"), enemies.get(2));

        Enemy.addEnemies(Difficulty.HARD, enemies);
        assertEquals(enemies.size(), 4);
        assertEquals(new Enemy("Warped Knight"), enemies.get(3));
    }
}