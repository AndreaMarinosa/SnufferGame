package com.mygdx.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kotcrab.vis.ui.VisUI;

public class ResourceManager {
    public static AssetManager assets = new AssetManager();

    public static void loadAllResources() {
        VisUI.load();
        /*
        // Sounds
        assets.load("audio/sounds/sound.ogg", Sound.class);

        // Music
        assets.load("audio/music/music.mp3", Music.class);

        // Texture
        assets.load("texture/gui/life.png", Texture.class);

        // Texture Atlas
        assets.load("texture/player/player_idle.pack", TextureAtlas.class);
      */

    }

    public static void finishLoading() {
        assets.finishLoading();
    }

    public static boolean update() {
        return assets.update();
    }

    public static TextureAtlas getAtlas(String path) {
        return assets.get(path, TextureAtlas.class);
    }

    public static Sound getSound(String path) {
        return assets.get(path, Sound.class);
    }

    public static Music getMusic(String path) {
        return assets.get(path, Music.class);
    }

    public static void dispose() {
        assets.dispose();
        VisUI.dispose();
    }

    public static Texture getTexture(String path) {
        return assets.get(path, Texture.class);
    }
}
