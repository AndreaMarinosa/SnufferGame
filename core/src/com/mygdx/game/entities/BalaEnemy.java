package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.enemy.Enem1;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.manager.SoundManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class BalaEnemy extends DinamicBody {

    public boolean active;
    public float velocidad= 3f;
    public GameScreen gameScreen;

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;

    private float progresoAnimacion = 0;

    private Estados ultimoEstado;
    private Estados estadoActual;

    public BalaEnemy(TiledMap map, World world, Rectangle bounds, GameScreen gameScreen) {
        super(map, world, bounds, null);

        this.gameScreen = gameScreen;
        active = false;
        fdef.filter.categoryBits = 128;
        fdef.filter.maskBits = 1 + 2; // 1 muros, 2 main character
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaBaj"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaUp"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaDech"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaIzq"));

        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;
    }

    @Override
    public void onContact(Contact contact) {
        if(contact.getFixtureA().getFilterData().categoryBits==1 || contact.getFixtureA().getFilterData().categoryBits==2){
            toDestroy=true;
        }else if(contact.getFixtureB().getFilterData().categoryBits==1 || contact.getFixtureB().getFilterData().categoryBits==2){
            toDestroy=true;
        }
    }

    /**
     * Dibuja la bala en la pantalla
     * @param dt
     * @param batch
     */
    @Override
    public void draw(float dt, Batch batch) {
        //super.draw(batch);
        TextureRegion tg =getFrame(dt);
        batch.draw(tg,body.getPosition().x, body.getPosition().y, tg.getRegionWidth()/6/ Constant.PPM,tg.getRegionHeight()/6/ Constant.PPM);
    }

    @Override
    public void postDraw(float dt, Batch batch) {

    }

    /**
     * Metodo que te actualiza la direccion a donde atacas
     * @param dt
     */
    @Override
    public void update(float dt) {

        //body.setLinearVelocity(new Vector2(velocidad, velocidad)); // Velocity
       // body.setLinearVelocity(direccion.x, direccion.y);

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

        }/*
        switch (direccion) {
            case 0: // DERECHA
                body.setLinearVelocity(new Vector2(velocidad, 0));
                break;
            case 1: // IZQUIERDA
                body.setLinearVelocity(new Vector2(-velocidad, 0));
                break;
            case 2: //ARIBA
                body.setLinearVelocity(new Vector2(0, velocidad));
                break;
            case 3: //ABAJO
                body.setLinearVelocity(new Vector2(0, -velocidad));
                break;

        }*/

    }

    /**
     * Coge la animación del ataque
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
