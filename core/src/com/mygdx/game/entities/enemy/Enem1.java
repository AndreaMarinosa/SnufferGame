package com.mygdx.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class Enem1 extends Enemy {

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;

    private GameScreen gameScreen;
    private float progresoAnimacion = 0;

    private Enem1.Estados ultimoEstado;
    private Enem1.Estados estadoActual;
    private float tTime = 0.3f;
    private float time = 0;
    private Vector2 direction = new Vector2();
    private Vector2 randomMovment = new Vector2(0.5f, 1f);

    private int vida;

    public Enem1(TiledMap map, World world, Rectangle bounds, GameScreen screen) {
        super(map, world, bounds);
        this.gameScreen = screen;

        fdef.filter.categoryBits = 8;
        fdef.filter.maskBits = 1 + 2 + 4 + 8 + 64; // 1 muro, 2 player, 4 bala, 8 enemigo, 64 wolf
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("frente"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("espalda"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("derecha"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("izquierda"));

        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;

        vida = 1;
    }

    @Override
    public void onContact(Contact contact) {
        if (contact.getFixtureA().getFilterData().categoryBits == 4 || contact.getFixtureA().getFilterData().categoryBits == 64) {
            vida--;
            if (vida <= 0){
                toDestroy = true;
                gameScreen.levelManager.player.aniquilados++;
                gameScreen.levelManager.player.contadorEnemigos++;
            }
        } else if (contact.getFixtureB().getFilterData().categoryBits == 4 || contact.getFixtureB().getFilterData().categoryBits == 64) {
            vida--;
            if (vida <= 0){
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
        if (time <= 0) {
            time = MathUtils.random(0.3f, 2f);

            if (MathUtils.random(0, 100) < 35 && body.getPosition().dst(gameScreen.levelManager.player.body.getPosition())>2) {

                time = MathUtils.random(1.1f, 2.5f);
                direction.x = MathUtils.random(-velocidad, velocidad);
                direction.y = MathUtils.random(-velocidad, velocidad);
            } else {
                time = tTime;

                if (body.getPosition().x < gameScreen.levelManager.player.body.getPosition().x) {
                    //body.setLinearVelocity(velocidad, body.getLinearVelocity().y);
                    direction.x = velocidad;
                }

                if (body.getPosition().x > gameScreen.levelManager.player.body.getPosition().x) {
                    //body.setLinearVelocity(-velocidad, body.getLinearVelocity().y);
                    direction.x = -velocidad;

                }
                if (body.getPosition().y < gameScreen.levelManager.player.body.getPosition().y) {
                    // body.setLinearVelocity(body.getLinearVelocity().x, velocidad);

                    direction.y = velocidad;

                }

                if (body.getPosition().y > gameScreen.levelManager.player.body.getPosition().y) {
                    //body.setLinearVelocity(body.getLinearVelocity().x, -velocidad);

                    direction.y = -velocidad;
                }
            }// Hasta a qui el random
            if (Math.abs(body.getLinearVelocity().y) < Math.abs(body.getLinearVelocity().x)) {
                if (body.getLinearVelocity().x > 0) {
                    estadoActual = Estados.DERECHA;
                } else if (body.getLinearVelocity().x < 0) {
                    estadoActual = Estados.IZQUIERDA;

                }
            } else {
                if (body.getLinearVelocity().y > 0) {
                    estadoActual = Estados.ESPALDAS;
                } else if (body.getLinearVelocity().y < 0) {
                    estadoActual = Estados.FRENTE;

                }

            }
        } else {
            body.setLinearVelocity(direction);
        }
        time -= dt;
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
