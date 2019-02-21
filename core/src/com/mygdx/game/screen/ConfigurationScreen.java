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
import com.mygdx.game.manager.ConfigurationManager;
import com.mygdx.game.manager.SoundManager;

public class ConfigurationScreen implements Screen {
    Stage stage;
    float volumenSonido;
    float volumenMusica;
    SGame game;

    public ConfigurationScreen(SGame game) {
        volumenSonido = ConfigurationManager.prefs.getFloat("soundVolume");
        volumenMusica = ConfigurationManager.prefs.getFloat("musicVolume");
        this.game = game;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }
        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        // si pulsa el boton que suba el sonido (max 1)
        VisTextButton btSubirSonido = new VisTextButton("Subir sonido");
        btSubirSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // subir sonido
                if (volumenSonido < 1f) {
                    ConfigurationManager.prefs.putFloat("soundVolume", volumenSonido += 0.1f);
                    SoundManager.playBala();

                }
            }
        });

        // si pulsa el boton que baje el sonido (min 0)
        VisTextButton btSBajarSonido = new VisTextButton("Bajar sonido");
        btSubirSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // bajar sonido
                if (volumenSonido > 0f) {
                    ConfigurationManager.prefs.putFloat("soundVolume", volumenSonido -= 0.1f);
                    SoundManager.playBala();

                }
            }
        });

        VisTextButton subirMusica = new VisTextButton("Subir musica");
        subirMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // subir sonido
                if (volumenMusica < 1f) {
                    ConfigurationManager.prefs.putFloat("soundMusic", volumenSonido += 0.1f);
                    SoundManager.playBala();

                }
            }

        });

        VisTextButton bajarMusica = new VisTextButton("Bajar musica");
        bajarMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // bajar sonido
                if (volumenSonido > 0f) {
                    ConfigurationManager.prefs.putFloat("soundMusic", volumenSonido -= 0.1f);
                    SoundManager.playBala();

                }
            }
        });

        VisTextButton btAtras = new VisTextButton("Atras");
        btAtras.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(btSubirSonido).center().width(200).height(50).pad(5);
        table.add(btSBajarSonido).center().width(200).height(50).pad(5);
        table.row();
        table.add(subirMusica).center().width(200).height(50).pad(5);
        table.add(bajarMusica).center().width(200).height(50).pad(5);
        table.row();
        table.add(btAtras).center().width(200).height(50).pad(5).colspan(2);


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
