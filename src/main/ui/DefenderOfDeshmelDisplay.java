package ui;

import model.Board;
import model.Enemy;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefenderOfDeshmelDisplay extends JFrame {

    private static final int INTERVAL = 20;

    private BoardPanel boardPanel;
    private Board board;

    public DefenderOfDeshmelDisplay() {
        super("Defender's Of Deshmel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        board = new Board();
        boardPanel = new BoardPanel(board);
        add(boardPanel);

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
