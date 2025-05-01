import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    public static void playSound(String path) {
        System.out.println("Trying to play: " + path);  // Debug message
        try {
            File soundFile = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Failed to play: " + path);
            e.printStackTrace();
        }
    }
}
