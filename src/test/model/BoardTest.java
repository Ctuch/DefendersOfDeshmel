package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    Person p1;
    Person p2;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        p1 = new Person("Ice Sorcerer");
        p2 = new Person("Foot Soldier");
        p1.setAttributes("Ice Sorcerer");
        p2.setAttributes("Foot Soldier");
    }

    @Test
    public void testAddCharacterToEmptySquare() {
        assertTrue(board.addCharacter(0, p1));
        assertEquals(board.getBoard().get(0).getName(), "Ice Sorcerer");
        assertTrue(board.addCharacter(15, p2));
        assertEquals(board.getBoard().get(15).getName(), "Foot Soldier");
    }

    @Test
    public void testAddCharacterNotAvailable() {
        assertTrue(board.addCharacter(24, p2));
        assertFalse(board.addCharacter(2, p2));
        assertNull(board.getBoard().get(2));

        p1.setAvailable(false);
        assertFalse(board.addCharacter(5, p1));
        assertNull(board.getBoard().get(5));

    }

    @Test
    public void testAddCharacterSquareFull() {
        board.addCharacter(14, p1);
        assertFalse(board.addCharacter(14, p2));
        assertEquals(board.getBoard().get(14), p1);
    }
}