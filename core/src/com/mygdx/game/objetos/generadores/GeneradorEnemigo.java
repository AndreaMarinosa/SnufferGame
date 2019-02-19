package com.mygdx.game.objetos.generadores;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.screen.GameScreen;

public class GeneradorEnemigo {
    private int aGenerar; // Nº de enemigos a generar
    private int generados;
    private float delay; // Delay de los enemigos cuando se generan
    private float lastDelay; // ultimo delay
    private GameScreen screen;
    private Vector2 posicion; // posicion donde se van a generar

    public GeneradorEnemigo(GameScreen screen, int aGenerar, float delay, Vector2 posicion) {
        this.screen = screen;
        this.aGenerar = aGenerar;
        this.delay = delay;
        this.posicion = posicion;

        generados=0;
        lastDelay = delay;
    }

    public void update(float dt){
        if (lastDelay<=0 && generados<aGenerar){
            generados++;
            lastDelay = delay;
            screen.levelManager.enemies.add(new Enem1(screen.levelManager.map,
                    screen.world, new Rectangle(posicion.x, posicion.y, 10, 10), screen));
        } else {
            lastDelay -= dt;
        }
    }
}
