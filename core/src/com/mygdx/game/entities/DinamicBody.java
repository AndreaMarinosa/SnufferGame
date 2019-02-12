package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class DinamicBody extends Sprite {

    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected World world;
    public boolean toDestroy;

    public DinamicBody(TiledMap map, World world, Rectangle bounds) {
        this.map = map;
        this.bounds = bounds;
        this.world = world;
        toDestroy = false;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2), (bounds.getY() + bounds.getHeight() / 2) );
        body = world.createBody(bdef);

        CircleShape body_shape = new CircleShape();
        body_shape.setRadius(bounds.getWidth()/2);
        body_shape.setPosition(new Vector2(bounds.getWidth()/2,bounds.getHeight()/2) );
        fdef.shape = body_shape;
        fdef.friction = 0.1f;
        fdef.filter.categoryBits = 2;
        fdef.filter.maskBits = 1;

        body.createFixture(fdef).setUserData(this);
    }

    public void destroyBody(){
        world.destroyBody(body);
    }

}
