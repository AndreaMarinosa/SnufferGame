package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SGame;
import com.mygdx.game.manager.ResourceManager;

public class Load implements Screen {
    private Texture splashTexture;
    private OrthographicCamera gamecam;

    private Viewport gamePort;
    private Image splashImage;
    private Stage stage;

    private boolean splashDone = false;
    private SGame game;

    public Load(SGame game) {
        this.game = game;
        splashTexture = new Texture(Gdx.files.internal("texture/images/splashscreen.jpg"));
        splashImage = new Image(splashTexture);
        splashImage.setPosition(0, 0);
        splashImage.setDrawable(new TextureRegionDrawable(new TextureRegion(splashTexture)));
        splashImage.setSize(1080, 720);

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(1080, 720, gamecam);
        stage = new Stage(gamePort);
        stage.addActor(splashImage);
    }

    private void update(float dt) {
        if (ResourceManager.update()) {
            if (splashDone) {
                game.setScreen(new GameScreen(game));
            }
        }
    }


    @Override
    public void show() {
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
                Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        splashDone = true;
                    }
                })
        ));
        ResourceManager.loadAllResources();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.disableBlending();
        stage.act();
        stage.draw();
        game.batch.enableBlending();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        splashTexture.dispose();
        stage.dispose();

    }
}
