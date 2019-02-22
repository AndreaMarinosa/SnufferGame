package com.mygdx.game.manager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.entities.enemy.Enem2;
import com.mygdx.game.objetos.Muro;
import com.mygdx.game.objetos.StaticBody;
import com.mygdx.game.objetos.cofres.Cofre;
import com.mygdx.game.objetos.generadores.Enemigo;
import com.mygdx.game.objetos.generadores.GeneradorEnemigo;
import com.mygdx.game.objetos.generadores.Enemigo2;
import com.mygdx.game.objetos.pinchos.Pincho;
import com.mygdx.game.screen.GameOverScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class LevelManager {
    public Array<Enemy> enemies;
    public Array<Bala> balas;
    public Array<BalaEnemy> balasEnemigos;
    public Array<Cofre> cofres;

    public Player player;
    public Wolf wolf;
    public TiledMap map;
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
        wolf = new Wolf(map, gameScreen.world,
                new Rectangle(120 +10 , 120+10, (32 / 2), (32 / 2) ), gameScreen);
        //nuevoJuego();
    }

    private void loadMap() {

        balas = new Array<Bala>();
        balasEnemigos = new Array<BalaEnemy>();
        enemies = new Array<Enemy>();
        cofres = new Array<Cofre>();

        map = new TmxMapLoader().load(mapa);
        gameScreen.mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ Constant.PPM);
        gameScreen.world.setContactListener(new ContactManager());


        //GRAOUND
        for (MapObject object : map.getLayers().get("wall").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Muro(rect, gameScreen, (short) 1);
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

        if (player.vida <= 0) {
            MusicManager.stopMusica();
            gameScreen.game.setScreen(new GameOverScreen(gameScreen.game, player.aniquilados));
            gameScreen.dispose();
        }

        suma = 0;

        for (GeneradorEnemigo e : generadores) {
            e.update(dt);
            suma =suma+ e.generados;

        }

        if(suma ==0){
            suma++;
        }

        if (player.contadorEnemigos >= suma) {
            for (GeneradorEnemigo e : generadores) {
                e.generados = 0;
            }
            RondaManager.nextRonda(gameScreen);
            player.contadorEnemigos = 0;
            for (MapObject object : map.getLayers().get("cofre").getObjects().getByType(RectangleMapObject.class)) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                cofres.add(new Cofre(rect, gameScreen, new Texture("core/assets/objetos/cofres/miniChest.png")));
            }
        }

        player.update(dt);
        wolf.update(dt);
        for(Cofre c :cofres){
            if(c.toDestroy){
                c.destroyBody();
                cofres.removeValue(c,true);
            }
        }

        for (Bala bala : balas) {
            if (bala.toDestroy) {
                bala.destroyBody();
                balas.removeValue(bala, true);
            } else {
                bala.update(dt);
            }
        }

        for (BalaEnemy balaEnemy : balasEnemigos) {
            if (balaEnemy.toDestroy) {
                balaEnemy.destroyBody();
                balasEnemigos.removeValue(balaEnemy, true);
            } else {
                balaEnemy.update(dt);
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
        wolf.draw(dt, bach);
        for (Bala bala : balas) {
            bala.draw(dt, bach);
        }
        for (BalaEnemy balaEnemy: balasEnemigos){
            balaEnemy.draw(dt, bach);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(dt, bach);
        }

        for (Cofre cofre : cofres) {
            cofre.draw(dt, bach);
        }

    }

    public void postRender(float dt, SpriteBatch bach) {
        player.postDraw(dt, bach);
    }

    public void nuevoJuego() {
        RondaManager.ronda = 1;
        RondaManager.delayEnemies = 0;
        RondaManager.aGenerar = 0;
        player.aniquilados = 0;
    }
}
