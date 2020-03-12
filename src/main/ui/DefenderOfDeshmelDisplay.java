package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DefenderOfDeshmelDisplay extends JFrame {

    private static final int INTERVAL = 20;

    private BoardPanel boardPanel;
    private OffBoardPersonPanel personPanel;
    private Board board;
    private ArrayList<Enemy> enemies;
    private ArrayList<Person> players;

    public DefenderOfDeshmelDisplay() {
        super("Defender's Of Deshmel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        board = new Board();
        enemies = new ArrayList<>();
        players = new ArrayList<>();

        boardPanel = new BoardPanel(board);
        personPanel = new OffBoardPersonPanel(players, enemies);
        add(boardPanel);
        add(personPanel, BorderLayout.SOUTH);

        pack();
        centreOnScreen();
        setVisible(true);
        //addTimer();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

}
