package com.mygdx.game.manager;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.objetos.generadores.GeneradorEnemigo;
import com.mygdx.game.screen.GameScreen;

/**
 * Clase que se encarga de las rondas por mapa
 *
 */
public class RondaManager {

    public static int ronda=0;
    public static int enemy = 5;

    public static float delayEnemies= 0;
    public static int aGenerar = 0;

    public static void nextRonda(GameScreen gameScreen){
        ronda++;
        System.out.println("Ronda "+ronda+" span evry="+delayEnemies);
        delayEnemies=1f-ronda/10f;
        // Si el delay de respawn de los enemigos es < o = a 0
        if(delayEnemies<=0){
            // le cambio el valor a 0.05
            delayEnemies=0.05f;
        }
        aGenerar = enemy+enemy*ronda;

    }

}
