package com.mygdx.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.BalaEnemy;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class Enem2 extends Enemy {

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;

    private GameScreen gameScreen;
    private float progresoAnimacion = 0;

    private Enem2.Estados ultimoEstado;
    private Enem2.Estados estadoActual;

    private float fireRate = 6f;
    private float fireLast = 0;
    private int vida;


    public Enem2(TiledMap map, World world, Rectangle bounds, GameScreen screen) {
        super(map, world, bounds);
        this.gameScreen = screen;
        this.gameScreen = screen;

        fdef.filter.categoryBits = 8;
        fdef.filter.maskBits = 1 + 2 + 4 + 8 + 64; // 1 muro, 2 player, 4 bala, 8 otros enemies, 64 wolf
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoDos/enemigoDos.pack").findRegions("frente"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoDos/enemigoDos.pack").findRegions("espalda"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoDos/enemigoDos.pack").findRegions("derecha"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoDos/enemigoDos.pack").findRegions("izquierda"));


        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;
        vida = 2;

    }

    @Override
    public void onContact(Contact contact) {
        if (contact.getFixtureA().getFilterData().categoryBits == 4 || contact.getFixtureA().getFilterData().categoryBits == 64) {
            vida--;
            System.out.println(vida);
            if (vida <= 0) {
                toDestroy = true;
                gameScreen.levelManager.player.aniquilados++;
                gameScreen.levelManager.player.contadorEnemigos++;
            }
        } else if (contact.getFixtureB().getFilterData().categoryBits == 4 || contact.getFixtureB().getFilterData().categoryBits == 64) {
            vida --;
            if (vida <= 0) {
                toDestroy = true;
                gameScreen.levelManager.player.aniquilados++;
                gameScreen.levelManager.player.contadorEnemigos++;
            }
        }
    }

    @Override
    public void draw(float dt, Batch batch) {
        TextureRegion tg = getFrame(dt);
        batch.draw(tg, body.getPosition().x - 4 / Constant.PPM, body.getPosition().y - 2 / Constant.PPM, (tg.getRegionWidth() / 4) / Constant.PPM, (tg.getRegionHeight() / 4) / Constant.PPM);
    }

    @Override
    public void postDraw(float dt, Batch batch) {

    }

    @Override
    public void update(float dt) {
        if (fireLast >= 0) {
            fireLast -= dt;
        }

        float distancia = gameScreen.levelManager.player.body.getPosition().dst(body.getPosition());

        if (distancia > 80 / Constant.PPM) {
            if (body.getPosition().x < gameScreen.levelManager.player.body.getPosition().x) {
                body.setLinearVelocity(velocidad, body.getLinearVelocity().y);

            } else if (body.getPosition().x > gameScreen.levelManager.player.body.getPosition().x) {
                body.setLinearVelocity(-velocidad, body.getLinearVelocity().y);
            }
            if (body.getPosition().y < gameScreen.levelManager.player.body.getPosition().y) {
                body.setLinearVelocity(body.getLinearVelocity().x, velocidad);
            } else if (body.getPosition().y > gameScreen.levelManager.player.body.getPosition().y) {
                body.setLinearVelocity(body.getLinearVelocity().x, -velocidad);
            }
        } else {
            if (fireLast <= 0) {
                gameScreen.levelManager.balasEnemigos.add(new BalaEnemy(map, world,
                        new Rectangle(body.getPosition().x * Constant.PPM, body.getPosition().y * Constant.PPM,
                                10, 10), gameScreen.levelManager.player.body.getPosition(), gameScreen));
                fireLast = fireRate;
            }
        }
    }

    /**
     * Metodo para coger el frae de cada animacion
     *
     * @param dt
     * @return
     */
    public TextureRegion getFrame(float dt) {

        progresoAnimacion = estadoActual == ultimoEstado ? progresoAnimacion + dt : 0;
        ultimoEstado = estadoActual;
        switch (estadoActual) {
            case FRENTE:
                return animacionFrente.getKeyFrame(progresoAnimacion, true);
            case DERECHA:
                return animacionDerecha.getKeyFrame(progresoAnimacion, true);
            case ESPALDAS:
                return animacionEspaldas.getKeyFrame(progresoAnimacion, true);
            case IZQUIERDA:
                return animacionIzquierda.getKeyFrame(progresoAnimacion, true);
            default:
                return animacionFrente.getKeyFrame(progresoAnimacion, true);
        }
    }

    //Estados
    private enum Estados {
        FRENTE, ESPALDAS, IZQUIERDA, DERECHA
    }
}
