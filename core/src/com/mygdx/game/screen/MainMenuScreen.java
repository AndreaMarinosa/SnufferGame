package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.mygdx.game.SGame;

public class MainMenuScreen implements Screen {
    SGame game;
    // private static MainMenuScreen ourInstance = new MainMenuScreen(game);
   // public static MainMenuScreen getInstance() {
   //     return ourInstance;
   // }

    Stage stage;
    public MainMenuScreen(SGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()){
            VisUI.load();
        }

        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        // si pulsa el boton play, que le lleve a jugar
        VisTextButton playButton = new VisTextButton("PLAY");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        VisTextButton configButton = new VisTextButton("CONFIGURATION");
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de configuración
                dispose();
            }
        });

        VisTextButton quitButton = new VisTextButton("QUIT");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                System.exit(0);
                // Salir del juego
            }
        });

        VisLabel aboutLabel = new VisLabel("Menu\n Andrea Mariñosa");

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(playButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(configButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(quitButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(aboutLabel).left().width(200).height(20).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Pinta la UI en la pantalla
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        stage.dispose();
    }
}
