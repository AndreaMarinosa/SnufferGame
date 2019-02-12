package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SGame;
import com.mygdx.game.manager.CameraManager;
import com.mygdx.game.manager.LevelManager;

public class GameScreen implements Screen {
    private final boolean gameOver;
    public final LevelManager levelManager;
    public final CameraManager cameraManager;
    public final Box2DDebugRenderer b2dr;
    public final World world;
    public OrthogonalTiledMapRenderer mapRenderer;
    private SGame game;

    public GameScreen(SGame game) {
        this.game = game;
        gameOver=false;
        levelManager = new LevelManager(this);
        cameraManager = new CameraManager(levelManager);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {

        world.step(Gdx.graphics.getDeltaTime(), 1, 1);
        mapRenderer.setView(cameraManager.cam);
        cameraManager.cam.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        game.batch.setProjectionMatrix(cameraManager.cam.combined);
        game.batch.begin();

        levelManager.render(delta,game.batch);

        game.batch.end();

        b2dr.render(world,cameraManager.cam.combined);
    }

    @Override
    public void resize(int width, int height) {
        cameraManager.port.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
