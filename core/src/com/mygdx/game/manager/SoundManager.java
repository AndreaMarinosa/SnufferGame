package com.mygdx.game.manager;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static Sound soundBala = ResourceManager.getSound("core/assets/audio/sounds/sonidoAtaque.ogg");
    private static Sound soundCofre = ResourceManager.getSound("core/assets/audio/sounds/cofre.ogg");
    private static Sound soundCogerCofre = ResourceManager.getSound("core/assets/audio/sounds/cogerCofre.ogg");
    private static Sound soundGameOver = ResourceManager.getSound("core/assets/audio/sounds/gameOver.ogg");

    public static void playBala() {
        soundBala.play(ConfigurationManager.prefs.getFloat("soundVolume", 0.3f));

    }

    public static void playCofre() {
        soundCofre.play(ConfigurationManager.prefs.getFloat("soundVolume", 0.3f));

    }

    public static void playCogerCofre() {
        soundCogerCofre.play(ConfigurationManager.prefs.getFloat("soundVolume", 0.3f));

    }

    public static void playGameOver() {
        soundGameOver.play(ConfigurationManager.prefs.getFloat("soundVolume", 0.3f));

    }
}
