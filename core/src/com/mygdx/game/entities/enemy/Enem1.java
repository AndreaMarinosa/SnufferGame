package com.mygdx.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;

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


    public Enem1(TiledMap map, World world, Rectangle bounds,GameScreen screen) {
        super(map, world, bounds);
        this.gameScreen=screen;
        this.gameScreen = screen;

        fdef.filter.categoryBits = 6;
        fdef.filter.maskBits = 1;
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("frente"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("espalda"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("derecha"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/enemigoUno/enemigoUno.pack").findRegions("izquierda"));


        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;

    }

    @Override
    public void onContact(Contact contact) {

    }

    @Override
    public void draw(float dt, Batch batch) {
        TextureRegion tg = getFrame(dt);
        batch.draw(tg, body.getPosition().x, body.getPosition().y, tg.getRegionWidth() / 6, tg.getRegionHeight() / 6);
    }

    @Override
    public void postDraw(float dt, Batch batch) {

    }

    @Override
    public void update(float dt) {
        if(body.getPosition().x<gameScreen.levelManager.player.body.getPosition().x){
            body.setLinearVelocity(210, body.getLinearVelocity().y);
        }else
        if(body.getPosition().x>gameScreen.levelManager.player.body.getPosition().x){
            body.setLinearVelocity(-210, body.getLinearVelocity().y);
        }
        if(body.getPosition().y<gameScreen.levelManager.player.body.getPosition().y){
            body.setLinearVelocity(body.getLinearVelocity().x, 210);
        }else
        if(body.getPosition().y>gameScreen.levelManager.player.body.getPosition().y){
            body.setLinearVelocity(body.getLinearVelocity().x, -210);
        }
    }

    @Override
    public void onHitPlayer() {

    }

    @Override
    public void onHitWall() {

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
