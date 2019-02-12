package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
        splashTexture = new Texture(Gdx.files.internal("texture/images/splashscreen.png"));
        splashImage = new Image(splashTexture);
        splashImage.setPosition(0, 0);
        splashImage.setDrawable(new TextureRegionDrawable(new TextureRegion(splashTexture)));
        splashImage.setSize(splashTexture.getWidth(), splashTexture.getHeight());

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(1080 , 720 , gamecam);
        stage = new Stage(gamePort);
        stage.addActor(splashImage);
    }

    private void update(float dt){
        if (ResourceManager.update()) {
            if (splashDone) {
                game.setScreen(new GameScreen(game));
            }
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
