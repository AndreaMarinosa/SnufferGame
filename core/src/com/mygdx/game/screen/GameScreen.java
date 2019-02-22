package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SGame;
import com.mygdx.game.manager.CameraManager;
import com.mygdx.game.manager.InputManager;
import com.mygdx.game.manager.LevelManager;
import com.mygdx.game.manager.MusicManager;

public class GameScreen implements Screen {
    private final boolean gameOver;
    public final LevelManager levelManager;
    public final CameraManager cameraManager;
    public final Box2DDebugRenderer b2dr;
    public BitmapFont font = new BitmapFont();
    public final World world;
    public OrthogonalTiledMapRenderer mapRenderer;
    public SGame game;
    private InputManager inputManager;
    private InputMultiplexer multiplexer;
    public static ShapeRenderer shapeRenderer = new ShapeRenderer();

    public GameScreen(SGame game, String mapa) {
        this.game = game;
        gameOver=false;
        world = new World(new Vector2(0, 0), true);
        levelManager = new LevelManager(this, mapa);
        cameraManager = new CameraManager(levelManager);
        b2dr = new Box2DDebugRenderer();
        cameraManager.cam.position.set(cameraManager.port.getWorldWidth() / 2,cameraManager.port.getWorldHeight() / 2, 0);
        inputManager =new InputManager(this);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputManager);
        Gdx.input.setInputProcessor(multiplexer);

        MusicManager.playMusica();
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {

        levelManager.update(delta);
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
        levelManager.postRender(delta,game.batch);
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
