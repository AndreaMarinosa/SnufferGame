package com.mygdx.game.manager;

import com.badlogic.gdx.audio.Music;

public class MusicManager {

    private static Music music = ResourceManager.getMusic("core/assets/audio/music/music.mp3");

    public static void playMusica() {
        music.setLooping(true);
        music.play();

    }

    public static void setVolumenMusica(float nuevo){
        music.setVolume(nuevo);
    }

    public static void stopMusica() {
        music.stop();
    }
}
