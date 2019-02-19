package com.mygdx.game.manager;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static Sound soundBala = ResourceManager.getSound("core/assets/audio/sounds/sonidoAtaque.ogg");

    public static void playBala(){
        if(ConfigurationManager.isSoundEnabled()) {
            soundBala.play();
        }
    }
}
