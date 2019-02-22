package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
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

        // Sounds
        assets.load("core/assets/audio/sounds/sonidoAtaque.ogg", Sound.class);
        assets.load("core/assets/audio/sounds/cogerCofre.ogg", Sound.class);
        assets.load("core/assets/audio/sounds/gameOver.ogg", Sound.class);
        assets.load("core/assets/audio/sounds/cofre.ogg", Sound.class);

                // Music
        assets.load("core/assets/audio/music/music.mp3", Music.class);

        // Texture
        assets.load("core/assets/objetos/cofres/miniChest.png", Texture.class);

        // Texture Atlas
        assets.load("core/assets/personajes/personajePrincipal/mainCharacter.pack", TextureAtlas.class);
        assets.load("core/assets/objetos/bala/bala.pack", TextureAtlas.class);
        assets.load("core/assets/personajes/enemigoUno/enemigoUno.pack", TextureAtlas.class);
        assets.load("core/assets/personajes/enemigoDos/enemigoDos.pack", TextureAtlas.class);
        assets.load("core/assets/personajes/zorro/zorro.pack", TextureAtlas.class);
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
