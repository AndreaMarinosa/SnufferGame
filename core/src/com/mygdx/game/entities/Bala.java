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
import com.mygdx.game.manager.ResourceManager;

public class Bala extends DinamicBody {

    public boolean active;
    public float shootTime;
    public float shootDuration=3;
    public float velocidad= 600f;

    private short direccion;

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;

    private float progresoAnimacion = 0;

    public Bala(TiledMap map, World world, Rectangle bounds, short direccion) {
        super(map, world, bounds, null);

        active = false;
        this.direccion = direccion;
        fdef.filter.categoryBits = 4;
        fdef.filter.maskBits = 1;
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaBaj"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaUp"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaDech"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/objetos/bala/bala.pack").findRegions("balaIzq"));

        setPosition(bounds.x / 2, bounds.y / 2);

    }

    @Override
    public void onContact(Contact contact) {
        System.out.println("en bala");
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
        batch.draw(tg,body.getPosition().x, body.getPosition().y, tg.getRegionWidth()/6,tg.getRegionHeight()/6);
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

        body.setLinearVelocity(new Vector2(velocidad, velocidad)); // Velocity

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

        }

        // seccion texturas
        //setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        //setRegion(getFrame(dt));
        // END seccion texturas
    }

    /**
     * Coge la animación del ataque
     * @param dt
     * @return
     */
    public TextureRegion getFrame(float dt) {

        switch (direccion) {
            case 3:
                return animacionFrente.getKeyFrame(progresoAnimacion, true);
            case 0:
                return animacionDerecha.getKeyFrame(progresoAnimacion, true);
            case 2:
                return animacionEspaldas.getKeyFrame(progresoAnimacion, true);
            case 1:
                return animacionIzquierda.getKeyFrame(progresoAnimacion, true);
            default:
                return animacionFrente.getKeyFrame(progresoAnimacion, true);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        this.active=true;
        shootTime=shootDuration;
    }

}
