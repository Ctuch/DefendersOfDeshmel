package model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    Person p1;
    Person p2;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        p1.setAttributes("Ice Sorcerer");
        p2.setAttributes("Foot Soldier");
    }


}