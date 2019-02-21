package com.mygdx.game.objetos.cofres;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.objetos.StaticBody;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by DAM on 21/02/2019.
 */
public class Cofre extends StaticBody {

    GameScreen screen;
    public Cofre(Rectangle rect, GameScreen gameScreen, short categoryBits) {
        super(rect, gameScreen, (short) 32);
        this.screen = gameScreen;
    }
}
