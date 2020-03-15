package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {
    private static final String EXPLOSION_SOUND = "./data/sounds/explosion.wav";
    private static final String SWORD_FIGHT_SOUND = "./data/sounds/.wav";
    private static final String LOSE_SOUND = "./data/sounds/lose.wav";
    private static final String WIN_SOUND = "./data/sounds/victory.wav";
    private static final String MOVE_SOUND = "./data/sounds/move.wav";
    private static final String ARROW_SOUND = "./data/sounds/arrow.wav";
    private static final String ADD_SOUND = "./data/sounds/add.wav";

    //from https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
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

    public void playSound(Sound sound) {
        switch (sound) {
            case EXPLOSION:
                playSound(EXPLOSION_SOUND);
                break;
            case SWORD_FIGHT:
                break;
            case DEAD:
                break;
            case WIN_PLAYER:
                playSound(WIN_SOUND);
                break;
            case WIN_ENEMY:
                playSound(LOSE_SOUND);
                break;
            case ARCHER:
                playSound(ARROW_SOUND);
                break;
            case MOVE:
                playSound(MOVE_SOUND);
                break;
            case ADD:
                playSound(ADD_SOUND);
                break;
        }
    }

    public enum Sound {
        EXPLOSION, SWORD_FIGHT, DEAD, WIN_PLAYER, WIN_ENEMY, ARCHER, MOVE, ADD
    }
}
