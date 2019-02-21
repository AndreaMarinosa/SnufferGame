package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ConfigurationManager {
	
	public static Preferences prefs = Gdx.app.getPreferences("Preferencias Juego");

	/**
	 * Comprueba si el sonido esta o no activado durante el juego
	 */
	public static boolean isSoundEnabled(){
		return prefs.getBoolean("sound", true);
		
	}
	
	/**
	 * Activa o desactiva el sonido
	 */
	public static void enableSound(boolean enable){
		prefs.putBoolean("sound", enable);
		// para que se escriba en el archivo de preferencias
		prefs.flush();
	}

	/**
	 * comprueba si la musica esta o no activada durante el juego
	 * @return
	 */
	public static boolean isMusicEnabled(){
		return prefs.getBoolean("music", true);

	}

	/**
	 * Activa o desactiva la musica
	 */
	public static void enableMusic(boolean enable){
		prefs.putBoolean("music", enable);
		// para que se escriba en el archivo de preferencias
		prefs.flush();
	}
	
}
