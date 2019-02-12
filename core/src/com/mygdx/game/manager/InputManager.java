package com.mygdx.game.manager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screen.GameScreen;

public class InputManager  implements InputProcessor {
    private GameScreen gameScreen;
    public boolean active;


    public InputManager(GameScreen gameScreen) {
        super();
        this.gameScreen = gameScreen;
        active=true;
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
