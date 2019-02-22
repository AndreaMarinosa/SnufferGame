package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class Wolf extends DinamicBody {

    //Estados
    private enum Estados {
        FRENTE, ESPALDAS, IZQUIERDA, DERECHA
    }

    public float velocidad;
    private GameScreen gameScreen;

    private Estados ultimoEstado;
    private Estados estadoActual;

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;
    private float progresoAnimacion = 0;

    private float tTime = 0.3f;
    private float time = 0;
    private Vector2 direction=new Vector2();

    public Wolf(TiledMap map, World world, Rectangle bounds, GameScreen gameScreen) {
        super(map, world, bounds, null);
        this.gameScreen = gameScreen;

        fdef.filter.categoryBits = 64;
        fdef.filter.maskBits = 1 + 2 + 8 + 16;// 1 + 6 (1 = muros, 8 = enemigos, 16 = pinchos, 32 = cofres)
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/zorro/zorro.pack").findRegions("frente"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/zorro/zorro.pack").findRegions("esp"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/zorro/zorro.pack").findRegions("dech"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/zorro/zorro.pack").findRegions("izq"));

        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;

        velocidad = 2f;

    }

    /*Podría haberte roto todo el código, no es muy recomendable dejarse cosas abiertas ;)*/
    @Override
    public void onContact(Contact contact) {

    }

    @Override
    public void draw(float dt, Batch batch) {
        TextureRegion tg = getFrame(dt);
        batch.draw(tg, body.getPosition().x-8/Constant.PPM, body.getPosition().y,
                (tg.getRegionWidth()/1.5f ) / Constant.PPM, (tg.getRegionHeight() /1.5f ) / Constant.PPM);
    }

    @Override
    public void postDraw(float dt, Batch batch) {
    }

    @Override
    public void update(float dt) {
        float distancia = gameScreen.levelManager.player.body.getPosition().dst(
                body.getPosition()
        );

        if (distancia >30/Constant.PPM) {
            if(time<=0) {
                time=tTime;
                if (body.getPosition().x < gameScreen.levelManager.player.body.getPosition().x) {
                    body.setLinearVelocity(velocidad, body.getLinearVelocity().y);
                    direction.x = velocidad;
                    direction.y=body.getLinearVelocity().y;

                } else if (body.getPosition().x > gameScreen.levelManager.player.body.getPosition().x) {
                    body.setLinearVelocity(-velocidad, body.getLinearVelocity().y);
                    direction.x = -velocidad;
                    direction.y=body.getLinearVelocity().y;
                }
                if (body.getPosition().y < gameScreen.levelManager.player.body.getPosition().y) {
                    body.setLinearVelocity(body.getLinearVelocity().x, velocidad);
                    direction.x = body.getLinearVelocity().x;
                    direction.y=velocidad;

                } else if (body.getPosition().y > gameScreen.levelManager.player.body.getPosition().y) {
                    body.setLinearVelocity(body.getLinearVelocity().x, -velocidad);
                    direction.x = body.getLinearVelocity().x;
                    direction.y=-velocidad;

                }

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
            }else{
                body.setLinearVelocity(direction);
            }
            time-=dt;
        }else{
            body.setLinearVelocity(0,0);
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

}