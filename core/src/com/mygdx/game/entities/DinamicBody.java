package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    protected BodyDef bdef ;
    protected FixtureDef fdef ;

    public DinamicBody(TiledMap map, World world, Rectangle bounds, TextureAtlas.AtlasRegion region) {
//        super(region);
        this.map = map;
        this.bounds = bounds;
        this.world = world;
        toDestroy = false;
        bdef = new BodyDef();
        fdef = new FixtureDef();
    }

    public void createBody(){
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2), (bounds.getY() + bounds.getHeight() / 2) );
        body = world.createBody(bdef);

        CircleShape body_shape = new CircleShape();
        body_shape.setRadius(bounds.getWidth()/1.7f);
        body_shape.setPosition(new Vector2(bounds.getWidth()/2,bounds.getHeight()/2) );
        fdef.shape = body_shape;
        body.createFixture(fdef).setUserData(this);
    }


    public void destroyBody(){
        world.destroyBody(body);
    }

    public abstract void onContact(Contact contact);
    public abstract void draw(float dt, Batch batch);
    public abstract void postDraw(float dt, Batch batch);
    public abstract void update(float dt);
}
