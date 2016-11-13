package com.chanhbc.caro.sounds;

public class PlayWavManager {
    private static PlayWavManager instance;
    private PlayWav soundClick;
    private PlayWav soundWin;
    private PlayWav soundLose;
    private PlayWav soundStart;
    private PlayWav soundBG;

    private PlayWavManager(){
        soundBG = new PlayWav("music");
    }

    public static PlayWavManager getInstance(){
        if(instance==null){
            instance = new PlayWavManager();
        }
        return instance;
    }

    public PlayWav getSoundClick() {
        soundClick = new PlayWav("click");
        return soundClick;
    }

    public PlayWav getSoundWin() {
        soundWin = new PlayWav("win");
        return soundWin;
    }

    public PlayWav getSoundLose() {
        soundLose = new PlayWav("lose");
        return soundLose;
    }

    public PlayWav getSoundStart() {
        soundStart = new PlayWav("start");
        return soundStart;
    }

    public PlayWav getSoundBG() {
        return soundBG;
    }
}
