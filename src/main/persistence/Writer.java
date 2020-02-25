package persistence;

import com.google.gson.Gson;
import model.Board;
import model.Enemy;
import model.Person;

import java.io.*;
import java.util.ArrayList;

// A writer that writes JSON objects in to a game save text file
public class Writer {
    private BufferedWriter bufferedWriter;

    //EFFECTS: creates a new writer to write to the file
    public Writer(File file) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(file));
    }

    //MODIFIES: file
    //EFFECTS: writes board, enemies, players, and walls as JSON strings on their own lines (in that order) to the file
    //         throws IOException if exception is raised writing to file
    public void write(Board board, ArrayList<Person> players, ArrayList<Enemy> enemies, int walls) throws IOException {
        Gson gson = new Gson();
        String boardString = gson.toJson(board.getBoard());
        String enemyString = gson.toJson(enemies);
        String playerString = gson.toJson(players);
        String wallConfigString = gson.toJson(walls);

        bufferedWriter.write(boardString);
        bufferedWriter.newLine();
        bufferedWriter.write(enemyString);
        bufferedWriter.newLine();
        bufferedWriter.write(playerString);
        bufferedWriter.newLine();
        bufferedWriter.write(wallConfigString);
        close();
    }

    //EFFECTS: writes over past save so that nothing can be read in if attempted to load file
    //         throws IOException if exception is raised writing to file
    public void clearSave() throws IOException {
        bufferedWriter.write("No save");
        close();
    }

    //MODIFIES: this
    //EFFECTS: closes bufferedWriter
    //         throws IOException if exception is raised closing writer
    private void close() throws IOException {
        bufferedWriter.close();
    }

}
