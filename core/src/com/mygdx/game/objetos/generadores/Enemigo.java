package com.mygdx.game.objetos.generadores;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.entities.enemy.Enem2;
import com.mygdx.game.screen.GameScreen;

public class Enemigo extends GeneradorEnemigo{


    public Enemigo(GameScreen screen, Vector2 posicion) {
        super(screen, posicion);
    }

    @Override
    protected void generate() {
        screen.levelManager.enemies.add(new Enem1(screen.levelManager.map,
                screen.world, new Rectangle(posicion.x, posicion.y, 10, 10), screen));
    }
}
