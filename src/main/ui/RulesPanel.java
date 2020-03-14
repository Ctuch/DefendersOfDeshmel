package ui;

import model.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RulesPanel extends JPopupMenu {

    private JTextArea rulesArea;
    private JButton closeButton;

    public RulesPanel(JButton closeButton) {
        setPreferredSize(new Dimension(800, 700));

        rulesArea = new JTextArea();
        readInHelp();
        rulesArea.setLineWrap(true);
        rulesArea.setPreferredSize(this.getPreferredSize());
        rulesArea.setForeground(Color.BLACK);
        this.closeButton = closeButton;


        add(rulesArea);
        add(closeButton, BorderLayout.SOUTH);

    }

    //EFFECTS: reads in rules from the rules.txt file
    private void readInHelp() {
        File rulesFile = new File("data/rules.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(rulesFile));
            rulesArea.read(br, "rulesArea");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("I'm sorry I guess you'll have to play without rules");
        }
    }
}
