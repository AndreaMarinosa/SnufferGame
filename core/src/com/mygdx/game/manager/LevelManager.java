package com.mygdx.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.screen.GameScreen;

public class LevelManager {
    private GameScreen gameScreen;
    private Array<Enem1> enemies;
    public TiledMap map;
    public LevelManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        loadMap();
    }

    private void loadMap(){

        map = new TmxMapLoader().load("level/level1/level1.tmx");
        gameScreen.mapRenderer = new OrthogonalTiledMapRenderer(map, 1);

        //gameScreen.world.setContactListener(new WorldContactListener());

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //GRAOUND
        for (MapObject object : map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2) );

            body = gameScreen.world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 , rect.getHeight() / 2 );
            fdef.shape = shape;
            fdef.filter.categoryBits =1;
            body.createFixture(fdef);
        }
    }


    public void update(float dt){

    }

    public void render(float dt, SpriteBatch bach) {

    }

    public void postRender(float dt, SpriteBatch bach) {

    }

}
