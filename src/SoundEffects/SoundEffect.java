package SoundEffects;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SoundEffect {
    public void play(String filename) {
        InputStream music;
        try {
            music = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void playLoop(String filename) {
        InputStream music;
        try {
            music = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            AudioStream audio = new AudioStream(music);
            AudioData audiodata = audio.getData();
            AudioPlayer.player.start(new ContinuousAudioDataStream(audiodata));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
