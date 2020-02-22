package persistence;

import com.google.gson.Gson;
import model.Board;
import model.Enemy;
import model.Person;

import java.io.*;
import java.util.ArrayList;

public class Writer {
    private BufferedWriter bufferedWriter;

    public Writer(File file) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(file));
    }

    public void write(Board board, ArrayList<Person> players, ArrayList<Enemy> enemies) throws IOException {
        Gson gson = new Gson();
        String boardString = gson.toJson(board.getBoard());
        String enemyString = gson.toJson(enemies);
        String playerString = gson.toJson(players);

        bufferedWriter.write(boardString);
        bufferedWriter.newLine();
        bufferedWriter.write(enemyString);
        bufferedWriter.newLine();
        bufferedWriter.write(playerString);
        close();
    }

    private void close() throws IOException {
        bufferedWriter.close();
    }
}
