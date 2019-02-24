package com.mygdx.game.objetos.cofres;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.manager.ConfigurationManager;
import com.mygdx.game.manager.SoundManager;
import com.mygdx.game.objetos.StaticBody;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

/**
 * Created by DAM on 21/02/2019.
 */
public class Cofre extends StaticBody {

    GameScreen screen;
    Texture tg;

    public Cofre(Rectangle rect, GameScreen gameScreen, Texture texture) {
        super(rect, gameScreen, (short) 32);
        this.screen = gameScreen;
        this.tg = texture;

        SoundManager.playCofre();
    }

    @Override
    public void draw(float dt, Batch batch) {
        batch.draw(tg, body.getPosition().x-18/Constant.PPM, body.getPosition().y-10/Constant.PPM,
                tg.getWidth()*2/ Constant.PPM, tg.getHeight()*2/ Constant.PPM);
    }
    public void destroyBody(){
        screen.world.destroyBody(body);
    }

    @Override
    public void onContact(Contact contact) {

    }


}
