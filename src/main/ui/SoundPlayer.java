package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// class for managing sound effects in the game
// all sounds were downloaded from freesound.org and are not under copyright.
// sounds were edited for use in this program after downloaded
public class SoundPlayer {
    private static final String EXPLOSION_SOUND = "./data/sounds/explosion.wav";
    private static final String SWORD_FIGHT_SOUND = "./data/sounds/swordClash.wav";
    private static final String LOSE_SOUND = "./data/sounds/lose.wav";
    private static final String WIN_SOUND = "./data/sounds/victory.wav";
    private static final String MOVE_SOUND = "./data/sounds/move.wav";
    private static final String ARROW_SOUND = "./data/sounds/arrow.wav";
    private static final String ADD_SOUND = "./data/sounds/add.wav";
    private static final String DEAD_SOUND =  "./data/sounds/dead.wav";
    private static final String SPECIAL_ACTION_SOUND =  "./data/sounds/dead.wav";


    //from https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
    //EFFECTS: plays the sound clip associated with filePath
    private void playSound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

    //TODO: finish implementing all sounds for all actions INCLUDING ENEMIES
    //EFFECTS: plays the sound associated with sound
    public void playSound(Sound sound) {
        if (sound == Sound.EXPLOSION) {
            playSound(EXPLOSION_SOUND);
        } else if (sound == Sound.SWORD_FIGHT) {
            playSound(SWORD_FIGHT_SOUND);
        } else if (sound == Sound.DEAD) {
            playSound(DEAD_SOUND);
        } else if (sound == Sound.WIN_PLAYER) {
            playSound(WIN_SOUND);
        } else if (sound == Sound.WIN_ENEMY) {
            playSound(LOSE_SOUND);
        } else if (sound == Sound.ARCHER) {
            playSound(ARROW_SOUND);
        } else if (sound == Sound.MOVE) {
            playSound(MOVE_SOUND);
        } else if (sound == Sound.ADD) {
            playSound(ADD_SOUND);
        } else if (sound == Sound.SPECIAL_ACTION) {
            playSound(SPECIAL_ACTION_SOUND);
        }
    }
}
