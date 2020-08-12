package SoundEffects;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SoundEffect {
    private String filename;


    public void play(String filename) {
        InputStream music;
        try {
            //String filename ="resources/Character_get_hit.wav";
            music = new FileInputStream(new File(filename));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
