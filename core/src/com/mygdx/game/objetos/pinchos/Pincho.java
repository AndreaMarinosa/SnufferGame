package com.mygdx.game.objetos.pinchos;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.DinamicBody;
import com.mygdx.game.objetos.StaticBody;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by DAM on 20/02/2019.
 */
public class Pincho extends StaticBody {

    GameScreen screen;
    // vida cuando pisas

    public Pincho(GameScreen screen, Rectangle posicion){
        super(posicion,screen, (short) 16);
        this.screen = screen;

    }
}
