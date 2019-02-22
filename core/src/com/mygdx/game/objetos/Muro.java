package com.mygdx.game.objetos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.screen.GameScreen;

/**
 * Created by DAM on 22/02/2019.
 */
public class Muro extends StaticBody {
    public Muro(Rectangle rect, GameScreen gameScreen, short categoryBits) {
        super(rect, gameScreen, categoryBits);
    }

    @Override
    public void onContact(Contact contact) {

    }

    @Override
    public void draw(float dt, Batch batch) {

    }
}
