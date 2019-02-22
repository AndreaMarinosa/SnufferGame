package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ConfigurationManager {

    public static Preferences prefs = Gdx.app.getPreferences("Preferencias Juego");


    public static void setSound(float nuevoSonido) {
        if (prefs.getFloat("soundVolume") < 0)
            prefs.putFloat("soundVolume", 0);
        else if (prefs.getFloat("soundVolume") > 1)
            prefs.putFloat("soundVolume", 1);
        else
            prefs.putFloat("soundVolume", nuevoSonido);
    }

    public static boolean isMusicEnabled() {
        return prefs.getBoolean("music", true);
    }

    public static void setMusicEnabled(boolean cambio) {
        prefs.putBoolean("music", cambio);
    }
}
