package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.util.dialog.ConfirmDialogListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.mygdx.game.SGame;
import com.mygdx.game.manager.RondaManager;
import com.mygdx.game.manager.SoundManager;

public class GameOverScreen implements Screen {

    private Stage stage;
    private SGame game;
    private int enemigos;


    public GameOverScreen(SGame game, int enemigos) {
        this.game = game;
        this.enemigos = enemigos;
    }

    @Override
    public void show() {
        stage = new Stage();
        SoundManager.playGameOver();

        VisLabel gameOver = new VisLabel("Game Over.");
        gameOver.setColor(Color.BLACK);
        stage.addActor(gameOver);

        VisLabel scoreEnemigos = new VisLabel("Enemigos muertos: " + enemigos);
        stage.addActor(scoreEnemigos);
        scoreEnemigos.setColor(Color.BLACK);

     /*   VisLabel scoreMayorEnemigos = new VisLabel("Max. Score enemigos: " + enemigosP);
        stage.addActor(scoreMayorEnemigos);
        scoreMayorEnemigos.setColor(Color.BLACK);*/

        VisLabel scoreRondas = new VisLabel("Rondas pasadas: " + RondaManager.ronda);
        stage.addActor(scoreRondas);
        scoreRondas.setColor(Color.BLACK);

     /*   VisLabel scoreMayorRondas= new VisLabel("Max. Score rondas: " + rondasP);
        stage.addActor(scoreMayorRondas);
        scoreMayorRondas.setColor(Color.BLACK);*/

        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        VisTextButton btMenu = new VisTextButton("MENU");
        btMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        VisTextButton btTryAgain = new VisTextButton("TRY AGAIN");
        btTryAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final int mapaUno = 1;
                final int mapaDos = 2;

                Dialogs.showConfirmDialog(stage, "Mapas", "Elije un mapa.",
                        new String[]{"MAPA 1", "MAPA 2"}, new Integer[]{mapaUno, mapaDos},
                        new ConfirmDialogListener<Integer>() {
                            @Override
                            public void result(Integer result) {
                                if (result == mapaUno) {
                                    game.setScreen(new GameScreen(game, "core/assets/level/Mapas/Mapa1.tmx"));
                                    dispose();
                                }
                                if (result == mapaDos) {
                                    game.setScreen(new GameScreen(game, "core/assets/level/Mapas/Mapa2.tmx"));
                                    dispose();
                                }

                            }
                        });
            }
        });

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(gameOver).left().width(200).height(20).pad(5).colspan(2);
        table.row();
        table.add(scoreEnemigos).left().width(200).height(20).pad(5).colspan(2);
       /* table.row();
        table.add(scoreMayorEnemigos).left().width(200).height(20).pad(5).colspan(2);*/
        table.row();
        table.add(scoreRondas).left().width(200).height(20).pad(5).colspan(2);
       /* table.row();
        table.add(scoreMayorRondas).left().width(200).height(20).pad(5).colspan(2);*/
        table.row();
        table.add(btMenu).center().width(200).height(50).pad(5);
        table.row();
        table.add(btTryAgain).center().width(200).height(50).pad(5);

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
