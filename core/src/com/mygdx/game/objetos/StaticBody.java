package com.mygdx.game.objetos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;



/**
 * Created by DAM on 20/02/2019.
 */
public class StaticBody {
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;

    public StaticBody(Rectangle rect, GameScreen gameScreen, short categoryBits) {
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constant.PPM, (rect.getY() + rect.getHeight() / 2) / Constant.PPM);

        body = gameScreen.world.createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2 / Constant.PPM, rect.getHeight() / 2 / Constant.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = categoryBits;
        body.createFixture(fdef);
    }
}
