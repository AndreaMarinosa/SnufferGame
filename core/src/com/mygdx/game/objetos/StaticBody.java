package com.mygdx.game.objetos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;



/**
 * Created by DAM on 20/02/2019.
 */
public abstract class StaticBody extends Contacto {
    protected BodyDef bdef = new BodyDef();
    public PolygonShape shape = new PolygonShape();
    protected FixtureDef fdef = new FixtureDef();
    public Body body;
    public boolean toDestroy;

    public StaticBody(Rectangle rect, GameScreen gameScreen, short categoryBits) {
        toDestroy = false;
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constant.PPM, (rect.getY() + rect.getHeight() / 2) / Constant.PPM);

        body = gameScreen.world.createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2 / Constant.PPM, rect.getHeight() / 2 / Constant.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = categoryBits;
        body.createFixture(fdef).setUserData(this);
    }


    public abstract void draw(float dt, Batch batch);

}
