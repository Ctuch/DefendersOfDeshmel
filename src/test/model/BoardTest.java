package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    Person iceSor;
    Person soldier;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        iceSor = new Person("Ice Sorcerer");
        soldier = new Person("Foot Soldier");
        board.setWallConfig(SquareWallConfigs.generateWallSetEmpty());
    }

    @Test
    public void testFillBoardWithNull() {
        for (Person p : board.getBoard()) {
            assertNull(p);
        }
    }

    @Test
    public void testAddCharacterToEmptySquare() {
        assertTrue(board.addCharacter(0, iceSor));
        assertEquals(board.getBoard().get(0).getName(), "Ice Sorcerer");
        assertTrue(board.addCharacter(15, soldier));
        assertEquals(board.getBoard().get(15).getName(), "Foot Soldier");
    }

    @Test
    public void testAddCharacterNotAvailable() {
        assertTrue(board.addCharacter(24, soldier));
        assertFalse(board.addCharacter(2, soldier));
        assertNull(board.getBoard().get(2));

        iceSor.setAvailable(false);
        assertFalse(board.addCharacter(5, iceSor));
        assertNull(board.getBoard().get(5));

    }

    @Test
    public void testAddCharacterSquareFull() {
        board.addCharacter(14, iceSor);
        assertFalse(board.addCharacter(14, soldier));
        assertEquals(board.getBoard().get(14), iceSor);
    }

    @Test
    public void testMoveCharacterNotDirection() {
        board.addCharacter(12, iceSor);
        assertFalse(board.moveCharacter(-1, iceSor));
        assertEquals(iceSor, board.getBoard().get(12));
    }

    @Test
    public void testMoveCharacterLeftValid() {
        board.addCharacter(12, iceSor);
        assertTrue(board.moveCharacter(Board.LEFT, iceSor));
        assertNull(board.getBoard().get(12));
        assertEquals(iceSor, board.getBoard().get(11));
    }

    @Test
    public void testMoveCharacterLeftInvalidOffBoard() {
        board.addCharacter(10, iceSor);
        assertFalse(board.moveCharacter(Board.LEFT, iceSor));
        assertEquals(iceSor, board.getBoard().get(10));
    }

    @Test
    public void testMoveCharacterLeftInvalidWall() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(12, iceSor);
        assertFalse(board.moveCharacter(Board.LEFT, iceSor));
        assertEquals(iceSor, board.getBoard().get(12));
    }

    @Test
    public void testMoveCharacterLeftInvalidFullSquare() {
        board.addCharacter(12, iceSor);
        board.addCharacter(11, soldier);
        assertFalse(board.moveCharacter(Board.LEFT, iceSor));
        assertEquals(board.getBoard().get(12), iceSor);
        assertEquals(soldier, board.getBoard().get(11));
    }

    @Test
    public void testMoveCharacterRightValid() {
        board.addCharacter(12, iceSor);
        assertTrue(board.moveCharacter(Board.RIGHT, iceSor));
        assertNull(board.getBoard().get(12));
        assertEquals(iceSor, board.getBoard().get(13));
    }

    @Test
    public void testMoveCharacterRightInvalidOffBoard() {
        board.addCharacter(14, iceSor);
        assertFalse(board.moveCharacter(Board.RIGHT, iceSor));
        assertEquals(iceSor, board.getBoard().get(14));
    }

    @Test
    public void testMoveCharacterRightInvalidWall() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(17, iceSor);
        assertFalse(board.moveCharacter(Board.RIGHT, iceSor));
        assertEquals(iceSor, board.getBoard().get(17));
    }

    @Test
    public void testMoveCharacterRightInvalidFullSquare() {
        board.addCharacter(3, iceSor);
        board.addCharacter(4, soldier);
        assertFalse(board.moveCharacter(Board.RIGHT, iceSor));
        assertEquals(board.getBoard().get(3), iceSor);
        assertEquals(soldier, board.getBoard().get(4));
    }

    @Test
    public void testMoveCharacterUpValid() {
        board.addCharacter(12, iceSor);
        assertTrue(board.moveCharacter(Board.UP, iceSor));
        assertNull(board.getBoard().get(12));
        assertEquals(iceSor, board.getBoard().get(7));
    }

    @Test
    public void testMoveCharacterUpInvalidOffBoard() {
        board.addCharacter(3, iceSor);
        assertFalse(board.moveCharacter(Board.UP, iceSor));
        assertEquals(iceSor, board.getBoard().get(3));
    }

    @Test
    public void testMoveCharacterUpInvalidWall() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(13, iceSor);
        assertFalse(board.moveCharacter(Board.UP, iceSor));
        assertEquals(iceSor, board.getBoard().get(13));
    }

    @Test
    public void testMoveCharacterUpInvalidFullSquare() {
        board.addCharacter(6, iceSor);
        board.addCharacter(1, soldier);
        assertFalse(board.moveCharacter(Board.UP, iceSor));
        assertEquals(board.getBoard().get(6), iceSor);
        assertEquals(soldier, board.getBoard().get(1));
    }

    @Test
    public void testMoveCharacterDownValid() {
        board.addCharacter(0, iceSor);
        assertTrue(board.moveCharacter(Board.DOWN, iceSor));
        assertNull(board.getBoard().get(0));
        assertEquals(iceSor, board.getBoard().get(5));
    }

    @Test
    public void testMoveCharacterDownInvalidOffBoard() {
        board.addCharacter(23, iceSor);
        assertFalse(board.moveCharacter(Board.DOWN, iceSor));
        assertEquals(iceSor, board.getBoard().get(23));
    }

    @Test
    public void testMoveCharacterDownInvalidWall() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        board.addCharacter(16, iceSor);
        assertFalse(board.moveCharacter(Board.DOWN, iceSor));
        assertEquals(iceSor, board.getBoard().get(16));
    }

    @Test
    public void testMoveCharacterDownInvalidFullSquare() {
        board.addCharacter(13, iceSor);
        board.addCharacter(18, soldier);
        assertFalse(board.moveCharacter(Board.DOWN, iceSor));
        assertEquals(board.getBoard().get(13), iceSor);
        assertEquals(soldier, board.getBoard().get(18));
    }

    @Test
    public void testMoveCharacterSquareFromSquareToUp() {
        board.addCharacter(12, iceSor);
        assertTrue(board.moveCharacter(12, 7));
        assertEquals(board.getBoard().get(7), iceSor);
    }


    @Test
    public void testMoveCharacterSquareFromSquareToDown() {
        board.addCharacter(15, iceSor);
        assertTrue(board.moveCharacter(15, 20));
        assertEquals(board.getBoard().get(20), iceSor);
    }

    @Test
    public void testMoveCharacterSquareFromSquareToRight() {
        board.addCharacter(2, iceSor);
        assertTrue(board.moveCharacter(2, 3));
        assertEquals(board.getBoard().get(3), iceSor);
    }

    @Test
    public void testMoveCharacterSquareFromSquareToLeft() {
        board.addCharacter(6, iceSor);
        assertTrue(board.moveCharacter(6, 5));
        assertEquals(board.getBoard().get(5), iceSor);
    }

    @Test
    public void testIsInWeaponRangeOneRightLeft() {
        board.addCharacter(9, iceSor);
        board.addCharacter(8, soldier);
        assertTrue(board.isInWeaponRange(iceSor, soldier));
        assertTrue(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeNotOneRightLeft() {
        board.addCharacter(9, iceSor);
        board.addCharacter(7, soldier);
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeOneUpDown() {
        board.addCharacter(5, iceSor);
        board.addCharacter(10, soldier);
        assertTrue(board.isInWeaponRange(iceSor, soldier));
        assertTrue(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeNotOneUpDown() {
        board.addCharacter(14, iceSor);
        board.addCharacter(4, soldier);
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeNotOneDiagonal() {
        board.addCharacter(9, iceSor);
        board.addCharacter(16, soldier);
        assertFalse(board.isInWeaponRange(soldier, iceSor));
        assertFalse(board.isInWeaponRange(iceSor, soldier));
    }

    @Test
    public void testIsInWeaponRangeThreeExactDistanceRightLeft() {
        board.addCharacter(0, iceSor);
        board.addCharacter(3, soldier);
        assertTrue(board.isInWeaponRange(iceSor, soldier));
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeThreeInexactDistanceRightLeft() {
        board.addCharacter(0, iceSor);
        board.addCharacter(2, soldier);
        assertTrue(board.isInWeaponRange(iceSor, soldier));
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeFourRightLeft() {
        board.addCharacter(0, iceSor);
        board.addCharacter(4, soldier);
        assertFalse(board.isInWeaponRange(iceSor, soldier));
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeThreeExactDistanceUpDown() {
        board.addCharacter(5, iceSor);
        board.addCharacter(20, soldier);
        assertTrue(board.isInWeaponRange(iceSor, soldier));
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeThreeInexactDistanceUpDown() {
        board.addCharacter(6, iceSor);
        board.addCharacter(16, soldier);
        assertTrue(board.isInWeaponRange(iceSor, soldier));
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testIsInWeaponRangeFiveUpDown() {
        board.addCharacter(2, iceSor);
        board.addCharacter(22, soldier);
        assertFalse(board.isInWeaponRange(iceSor, soldier));
        assertFalse(board.isInWeaponRange(soldier, iceSor));
    }

    @Test
    public void testFindCharacterCode() {
        board.addCharacter(3, iceSor);
        Person findable = board.findPersonByCharacterCode("iC");
        assertEquals(findable, iceSor);
        Person notFindable = board.findPersonByCharacterCode("ww");
        assertNull(notFindable);
    }

    @Test
    public void testGetWallConfig() {
        assertEquals(board.getWallConfig().size(), 25);
    }

    @Test
    public void testSetWallConfig() {
        board.setWallConfig(SquareWallConfigs.generateWallSetOne());
        assertFalse(board.getWallConfig().get(7).isLeftWall());
        assertTrue(board.getWallConfig().get(7).isRightWall());
        assertFalse(board.getWallConfig().get(7).isUpperWall());
        assertFalse(board.getWallConfig().get(7).isLowerWall());
        assertTrue(board.getWallConfig().get(16).isLeftWall());

        board.setWallConfig(SquareWallConfigs.generateWallSetEmpty());
        for (int i = 0; i < board.getWallConfig().size(); i++) {
            SquareWall wall = board.getWallConfig().get(i);
            assertFalse(wall.isLeftWall());
            assertFalse(wall.isRightWall());
            assertFalse(wall.isUpperWall());
            assertFalse(wall.isLowerWall());
        }
    }

    @Test
    public void testSetBoard() {
        ArrayList<Person> board2 = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            board2.add(null);
        }
        board2.set(3, new Person("Foot Soldier"));
        board2.set(15, new Person("Fire Sorceress"));
        board.setBoard(board2);
        assertEquals(board2.get(3), new Person("Foot Soldier"));
        assertEquals(board2.get(15), new Person("Fire Sorceress"));
    }

    @Test
    public void testResetBoard() {
        board.addCharacter(3, iceSor);
        board.addCharacter(7, soldier);
        int wallConfigNum = SquareWallConfigs.getWallSetNum();
        board.resetBoard();
        //TODO: this is not the best way to do this, update once more wall configs are generated
        assertEquals(wallConfigNum, SquareWallConfigs.getWallSetNum());
        assertFalse(board.getBoard().contains(iceSor));
        assertFalse(board.getBoard().contains(soldier));
    }

    @Test
    public void testFindEmptySquare() {
        for (int i = 0; i < 25; i++) {
            board.getBoard().set(i, new Person("Fire Sorceress"));
        }
        board.getBoard().set(13, null);
        Enemy enemy = new Enemy("Foot Soldier");
        board.findEmptySquare(enemy);
        assertTrue(board.getBoard().contains(enemy));
        assertEquals(board.getBoard().get(13), enemy);
    }

    @Test
    public void testRemoveDeadDefender() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Person> players = new ArrayList<>();
        Enemy enemy = new Enemy("Foot Soldier");
        enemies.add(enemy);
        players.add(iceSor);
        board.addCharacter(6, iceSor);
        board.addCharacter(20, enemy);

        iceSor.takeDamage(iceSor.getHealth());

        board.removeDeadDefender(iceSor, enemies, players);
        assertNull(board.getBoard().get(6));
        assertFalse(players.contains(iceSor));

        enemy.takeDamage(enemy.getHealth());

        board.removeDeadDefender(enemy, enemies, players);
        assertNull(board.getBoard().get(20));
        assertFalse(enemies.contains(enemy));
    }
}