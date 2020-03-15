package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

//popup window for displaying the rules to the player that overlays the game
public class RulesPanel extends JPopupMenu {

    private JTextArea rulesArea;

    //EFFECTS: sets the size of the panel and reads the rules file and displays its text
    public RulesPanel() {
        setPreferredSize(new Dimension(800, 700));

        rulesArea = new JTextArea();
        readInHelp();
        rulesArea.setLineWrap(true);
        rulesArea.setPreferredSize(this.getPreferredSize());
        rulesArea.setForeground(Color.BLACK);

        add(rulesArea);
    }

    //MODIFIES: this
    //EFFECTS: reads in rules from the rules.txt file
    public String readInHelp() {
        File rulesFile = new File("data/rules.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(rulesFile));
            rulesArea.read(br, "rulesArea");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("I'm sorry I guess you'll have to play without rules");
        }
        return rulesArea.getText();
    }
}
