package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void testFillBoardWithNull() {
        for (Person p: board.getBoard()) {
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
    public void testMoveCharacterDownInvalidFullSquare() {
        board.addCharacter(13, iceSor);
        board.addCharacter(18, soldier);
        assertFalse(board.moveCharacter(Board.DOWN, iceSor));
        assertEquals(board.getBoard().get(13), iceSor);
        assertEquals(soldier, board.getBoard().get(18));
    }

    @Test
    public void testIsAdjacentLeftRightAdj() {
        board.addCharacter(13, iceSor);
        board.addCharacter(12, soldier);
        assertTrue(board.isAdjacent(iceSor, soldier));
        assertTrue(board.isAdjacent(soldier, iceSor));
    }

    @Test
    public void testIsAdjacentUpDownAdj() {
        board.addCharacter(11, iceSor);
        board.addCharacter(16, soldier);
        assertTrue(board.isAdjacent(iceSor, soldier));
        assertTrue(board.isAdjacent(soldier, iceSor));
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
}