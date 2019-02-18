package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;

public class Player extends DinamicBody {

    public boolean inputUp = false;
    public boolean inputDown = false;
    public boolean inputLeft = false;
    public boolean inputRight = false;

    public boolean inputDispararUp = false;
    public boolean inputDispararRight = false;
    public boolean inputDispararLeft = false;
    public boolean inputDispararDown = false;

    private GameScreen gameScreen;

    //Estados
    private enum Estados {
        FRENTE, ESPALDAS, IZQUIERDA, DERECHA
    }

    ;
    private Estados ultimoEstado;
    private Estados estadoActual;

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;

    private float progresoAnimacion = 0;

    public Player(TiledMap map, World world, Rectangle bounds, GameScreen gameScreen) {
        super(map, world, bounds, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegion("frente"));

        this.gameScreen = gameScreen;

        fdef.filter.categoryBits = 2;
        fdef.filter.maskBits = 1;
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("frente"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("espalda"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("derecha"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("izquierda"));

        setPosition(bounds.x / 2, bounds.y / 2);

        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;

        System.out.println("animacionFrente: "+animacionFrente.getKeyFrames().length);
        System.out.println("animacionEspaldas: "+animacionEspaldas.getKeyFrames().length);
        System.out.println("animacionDerecha: "+animacionDerecha.getKeyFrames().length);
        System.out.println("animacionIzquierda: "+animacionIzquierda.getKeyFrames().length);
    }

    @Override
    public void onContact(Contact contact) {
        System.out.println("en player");
    }

    @Override
    public void draw(float dt, Batch batch) {
        //super.draw(batch);
        TextureRegion tg =getFrame(dt);
        batch.draw(tg,body.getPosition().x, body.getPosition().y, tg.getRegionWidth()/6,tg.getRegionHeight()/6);
    }

    @Override
    public void postDraw(float dt, Batch batch) {

    }

    @Override
    public void update(float dt) {
        menageInput();

        // seccion texturas
        //setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        //setRegion(getFrame(dt));
        // END seccion texturas
    }

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


    private void menageInput() {

        // JUGADOR
        if (inputUp) {
            body.setLinearVelocity(body.getLinearVelocity().x, 200);
            estadoActual = Estados.ESPALDAS;
        } else if (inputDown) {
            estadoActual = Estados.FRENTE;
            body.setLinearVelocity(body.getLinearVelocity().x, -200);
        }


        if (inputLeft) {
            estadoActual = Estados.IZQUIERDA;
            body.setLinearVelocity(-200, body.getLinearVelocity().y);
        } else if (inputRight) {
            estadoActual = Estados.DERECHA;
            body.setLinearVelocity(200, body.getLinearVelocity().y);
        }

        if (!inputUp && !inputDown) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        if (!inputLeft && !inputRight) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }


        if(inputDispararRight || inputDispararLeft || inputDispararUp || inputDispararDown){
            if(inputDispararRight){
                gameScreen.levelManager.balas.add(new Bala(map,world,new Rectangle(body.getPosition().x , body.getPosition().y,10,10), (short) 0));
            }else if(inputDispararLeft){
                gameScreen.levelManager.balas.add(new Bala(map,world,new Rectangle(body.getPosition().x , body.getPosition().y,10,10), (short) 1));
            }else if(inputDispararUp){
                gameScreen.levelManager.balas.add(new Bala(map,world,new Rectangle(body.getPosition().x , body.getPosition().y,10,10), (short) 2));
            }else if(inputDispararDown){
                gameScreen.levelManager.balas.add(new Bala(map,world,new Rectangle(body.getPosition().x , body.getPosition().y,10,10), (short) 3));
            }


        }

    }


}