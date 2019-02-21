package com.mygdx.game.objetos.generadores;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.manager.RondaManager;
import com.mygdx.game.screen.GameScreen;

public abstract class GeneradorEnemigo {

    public int generados;

    protected float lastDelay; // ultimo delay
    protected GameScreen screen;
    protected Vector2 posicion; // posicion donde se van a generar

    public GeneradorEnemigo(GameScreen screen,  Vector2 posicion) {
        this.screen = screen;
        this.posicion = posicion;
        generados=0;
        lastDelay = RondaManager.delayEnemies;
    }

    public void update(float dt){
        if (lastDelay<=0 && generados<RondaManager.aGenerar){
            generados++;
            lastDelay = RondaManager.delayEnemies;
            generate();

        } else {
            lastDelay -= dt;
        }
    }

    protected abstract void generate();
}
