package com.chanhbc.caro.sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class PlayWav {
    private Clip clip;

    public PlayWav(String filename) {
        try {
            URL url = getClass().getResource("/res/sounds/" + filename + ".wav");
            clip = AudioSystem.getClip();
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clip.open(audio);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playWav() {
        if (clip.isOpen() && !clip.isRunning()) {
            clip.start();
        }
    }

    public void stopWav() {
        clip.stop();
    }

    public void loop(int number) {
        if (number >= 0)
            clip.loop(number);
        else
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public boolean running(){
        return clip.isRunning();
    }
}
