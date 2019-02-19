package com.mygdx.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Bala;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Player;
import com.mygdx.game.objetos.generadores.GeneradorEnemigo;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class LevelManager {
    public Array<Enemy> enemies;
    public Array<Bala> balas;
    public Player player;
    public TiledMap map;
    public mapas estadoMapa = mapas.MAPA1;
    private GameScreen gameScreen;

    private GeneradorEnemigo gEnemigo; // Generados de enemigos

    public LevelManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        loadMap();

        player = new Player(map, gameScreen.world, new Rectangle(120/ Constant.PPM, 120/ Constant.PPM,
                (32 / 2)/ Constant.PPM, (32 / 2)/ Constant.PPM), gameScreen);

        gEnemigo = new GeneradorEnemigo(gameScreen, 10, 1, new Vector2(176, 336));
    }

    private void loadMap() {

        balas = new Array<Bala>();
        enemies = new Array<Enemy>();

        switch (estadoMapa) {
            case MAPA1: {
                map = new TmxMapLoader().load("core/assets/level/Mapas/Mapa1.tmx");
                break;
            }

            case MAPA2: {
                map = new TmxMapLoader().load("core/assets/level/Mapas/Mapa2.tmx");
                break;
            }
            case MAPA3: {

                break;
            }
            case MAPA4: {

                break;
            }
            default:
                map = new TmxMapLoader().load("core/assets/level/Mapas/Mapa1.tmx");
                break;
        }

        gameScreen.mapRenderer = new OrthogonalTiledMapRenderer(map, (1/ Constant.PPM));
        gameScreen.world.setContactListener(new ContactManager());
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //GRAOUND
        for (MapObject object : map.getLayers().get("wall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constant.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Constant.PPM);

            body = gameScreen.world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constant.PPM,
                    (rect.getHeight() / 2) / Constant.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = 1;
            body.createFixture(fdef);
        }
    }

    public void update(float dt) {
        player.update(dt);
        for (Bala bala : balas) {
            if (bala.toDestroy){
                bala.destroyBody();
                balas.removeValue(bala, true);
            } else{
                bala.update(dt);
            }
        }

        gEnemigo.update(dt);
        for (Enemy enemigo: enemies){
            if (enemigo.toDestroy){
                enemigo.destroyBody();
                enemies.removeValue(enemigo, true);
            } else {
                enemigo.update(dt);
            }
        }

    }

    public void render(float dt, SpriteBatch bach) {
        player.draw(dt, bach);
        for (Bala bala : balas) {
            bala.draw(dt, bach);
        }
        for (Enemy enemy: enemies){
            enemy.draw(dt, bach);
        }

    }

    public void postRender(float dt, SpriteBatch bach) {
        player.postDraw(dt, bach);
    }

    public enum mapas {
        MAPA1, MAPA2, MAPA3, MAPA4
    }

}
