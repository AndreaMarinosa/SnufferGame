package com.mygdx.game.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.util.Constant;

public class CameraManager {
    private LevelManager levelManager;
    public OrthographicCamera cam;
    public Viewport port;

    public CameraManager(LevelManager levelManager) {
        this.levelManager = levelManager;
        cam = new OrthographicCamera();
        port = new FitViewport(Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT, cam);
    }
}
