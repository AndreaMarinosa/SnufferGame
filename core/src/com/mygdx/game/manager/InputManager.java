package com.mygdx.game.manager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MainMenuScreen;

public class InputManager  implements InputProcessor {
    private GameScreen gameScreen;
    public boolean active;
    float sonido;
    float musica;

    public InputManager(GameScreen gameScreen) {
        super();
        this.gameScreen = gameScreen;
        active=true;
        sonido = ConfigurationManager.prefs.getFloat("soundVolume");
        musica = ConfigurationManager.prefs.getFloat("musicVolume");
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                gameScreen.levelManager.player.inputLeft = true;
                break;
            case Input.Keys.D:
                gameScreen.levelManager.player.inputRight = true;
                break;
            case Input.Keys.S:
                gameScreen.levelManager.player.inputDown = true;
                break;
            case Input.Keys.W:
                gameScreen.levelManager.player.inputUp = true;
                break;
            case Input.Keys.UP:
                gameScreen.levelManager.player.inputDispararUp = true;
                break;
            case Input.Keys.DOWN:
                gameScreen.levelManager.player.inputDispararDown = true;
                break;
            case Input.Keys.RIGHT:
                gameScreen.levelManager.player.inputDispararRight = true;
                break;
            case Input.Keys.LEFT:
                gameScreen.levelManager.player.inputDispararLeft = true;
                break;
            case Input.Keys.ESCAPE:
                MusicManager.stopMusica();
                gameScreen.game.setScreen(new MainMenuScreen(gameScreen.game));
                gameScreen.dispose();
                break;
            case Input.Keys.Z: // bajar sonido
                if (ConfigurationManager.prefs.getFloat("soundVolume") > 0f)
                ConfigurationManager.setSound(sonido-=0.1000f);
                break;
            case Input.Keys.X: // subir sonido
                if (ConfigurationManager.prefs.getFloat("soundVolume") < 1.00f)
                ConfigurationManager.setSound(sonido+=0.1000f);
                break;
            case Input.Keys.C: // bajar musica
                MusicManager.stopMusica();
                break;
            case Input.Keys.V: // subir musica
                MusicManager.playMusica();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                gameScreen.levelManager.player.inputLeft = false;
                break;
            case Input.Keys.D:
                gameScreen.levelManager.player.inputRight = false;
                break;
            case Input.Keys.S:
                gameScreen.levelManager.player.inputDown = false;
                break;
            case Input.Keys.W:
                gameScreen.levelManager.player.inputUp = false;
                break;
            case Input.Keys.UP:
                gameScreen.levelManager.player.inputDispararUp = false;
                break;
            case Input.Keys.DOWN:
                gameScreen.levelManager.player.inputDispararDown = false;
                break;
            case Input.Keys.RIGHT:
                gameScreen.levelManager.player.inputDispararRight = false;
                break;
            case Input.Keys.LEFT:
                gameScreen.levelManager.player.inputDispararLeft = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
