package com.mygdx.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Bala;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.entities.enemy.Enem2;
import com.mygdx.game.objetos.StaticBody;
import com.mygdx.game.objetos.generadores.Enemigo;
import com.mygdx.game.objetos.generadores.GeneradorEnemigo;
import com.mygdx.game.objetos.generadores.Enemigo2;
import com.mygdx.game.objetos.pinchos.Pincho;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;
import com.mygdx.game.util.Funciones;

import static com.mygdx.game.manager.LevelManager.mapas.MAPA1;
import static com.mygdx.game.manager.LevelManager.mapas.MAPA2;

public class LevelManager {
    public Array<Enemy> enemies;
    public Array<Bala> balas;
    public static Player player;
    public TiledMap map;
    public mapas estadoMapa = MAPA1;
    private GameScreen gameScreen;
    String mapa;
    public Array<GeneradorEnemigo> generadores;
    private int suma=0;

    public LevelManager(GameScreen gameScreen, String mapa) {
        this.mapa = mapa;
        this.gameScreen = gameScreen;
        RondaManager.nextRonda(gameScreen);
        generadores = new Array<GeneradorEnemigo>();
        loadMap();
        player = new Player(map, gameScreen.world,
                new Rectangle(120 , 120, (32 / 2), (32 / 2) ), gameScreen);

    }

    private void loadMap() {

        balas = new Array<Bala>();
        enemies = new Array<Enemy>();


        map = new TmxMapLoader().load(mapa);
        gameScreen.mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ Constant.PPM);
        gameScreen.world.setContactListener(new ContactManager());


        //GRAOUND
        for (MapObject object : map.getLayers().get("wall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new StaticBody(rect, gameScreen, (short) 1);
        }
        for (MapObject object : map.getLayers().get("generador").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            System.out.println(rect.x + ", " + rect.y);
            if ( object.getProperties().get("tipo",Integer.class) == 1) {
               generadores.add(new Enemigo2(gameScreen, new Vector2(rect.x , rect.y )));
            } else {
               generadores.add(new Enemigo(gameScreen, new Vector2(rect.x , rect.y )));
            }

        }
        try {
            for (MapObject object : map.getLayers().get("pinchos").getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Pincho(gameScreen, rect);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void update(float dt) {
        suma = 0;

        for (GeneradorEnemigo e : generadores) {
            e.update(dt);
            suma =suma+ e.generados;

        }

        if(suma ==0){
            suma =1;
        }

        if (player.aniquilados >= suma) {
            for (GeneradorEnemigo e : generadores) {
                e.generados = 0;
            }
            RondaManager.nextRonda(gameScreen);
            player.aniquilados = 0;
        }

        if (RondaManager.ronda == 1 && estadoMapa == MAPA1) {
            Enem1.velocidad-=0.5f; // bajamos la velocidad del enemigo
            Enem2.velocidad-=0.5f;
            estadoMapa = MAPA2;
            gameScreen.game.setScreen(new GameScreen(gameScreen.game, "core/assets/level/Mapas/Mapa2.tmx"));
            gameScreen.dispose();
        }


        player.update(dt);
        for (Bala bala : balas) {
            if (bala.toDestroy) {
                bala.destroyBody();
                balas.removeValue(bala, true);
            } else {
                bala.update(dt);
            }
        }

        for (Enemy enemigo : enemies) {
            if (enemigo.toDestroy) {
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
        for (Enemy enemy : enemies) {
            enemy.draw(dt, bach);
        }

    }

    public void postRender(float dt, SpriteBatch bach) {
        player.postDraw(dt, bach);
    }

    public static void nuevoJuego() {
        RondaManager.ronda = 0;
        RondaManager.enemy = 5;
        RondaManager.delayEnemies = 0;
        RondaManager.aGenerar = 0;
        player.aniquilados = 0;

    }

    public enum mapas {
        MAPA1, MAPA2, MAPA3, MAPA4
    }

}
