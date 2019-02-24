package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.mygdx.game.manager.RondaManager;
import com.mygdx.game.util.Constant;

public class Hud implements Disposable {
    public Stage stage;
    protected Viewport viewPort;
    protected GameScreen screen;
    VisLabel vida;
    VisLabel fps;
    VisLabel ronda;
    VisLabel eliminaciones;
    static final float fontScale = 0.75f;

    public Hud(GameScreen screen) {
        viewPort = new FitViewport(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort);
        this.screen = screen;

        VisLabel labelVida = new VisLabel("V: ");
        labelVida.setFontScale(fontScale);

        VisLabel labelPuntos = new VisLabel("E: ");
        labelPuntos.setFontScale(fontScale);

        VisLabel labelRondas= new VisLabel("R: ");
        labelRondas.setFontScale(fontScale);

        VisLabel labelFps = new VisLabel("FPS: ");
        labelFps.setFontScale(fontScale);

        vida = new VisLabel("100");
        vida.setFontScale(fontScale);
        fps = new VisLabel("0");
        fps.setFontScale(fontScale);
        eliminaciones = new VisLabel("0");
        eliminaciones.setFontScale(fontScale);
        ronda = new VisLabel("0");
        ronda.setFontScale(fontScale);

        VisTable table = new VisTable();
        table.setFillParent(false);
        table.setPosition(0, Constant.SCREEN_HEIGHT);

        table.row().height(10);
        table.add(labelVida).width(40);
        table.add(vida).width(40);

        table.row().height(10);
        table.add(labelRondas).width(40);
        table.add(ronda).width(40);

        table.row().height(10);
        table.add(labelPuntos).width(40);
        table.add(eliminaciones).width(40);

        table.row().height(10);
        table.add(labelFps).width(40);
        table.add(fps).width(40);

        //  table.setDebug(true);
        table.setPosition(58, Constant.SCREEN_HEIGHT - 10 - (table.getRows() * 10));
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float delta) {
        this.vida.setText(screen.levelManager.player.vida+"");
        ronda.setText(RondaManager.ronda+"");
        eliminaciones.setText(screen.levelManager.player.aniquilados+"");
        fps.setText(" "+ Gdx.graphics.getFramesPerSecond());
    }
}
