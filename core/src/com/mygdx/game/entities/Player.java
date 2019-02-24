package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.manager.SoundManager;
import com.mygdx.game.objetos.cofres.Cofre;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.util.Constant;

public class Player extends DinamicBody {

    public int aniquilados = 0; // Contador de enemigos matados
    public boolean inputUp = false;
    public boolean inputDown = false;
    public boolean inputLeft = false;
    public boolean inputRight = false;
    public boolean inputDispararUp = false;
    public boolean inputDispararRight = false;
    public boolean inputDispararLeft = false;
    public boolean inputDispararDown = false;
    public int contadorEnemigos;
    public float velocidad = 2f;
    public float vida;
    private GameScreen gameScreen;
    public float fireRate = 0.2f;
    private float fireLast = 0;

    private Estados ultimoEstado;
    private Estados estadoActual;

    // Animaciones
    private Animation<TextureRegion> animacionFrente;
    private Animation<TextureRegion> animacionEspaldas;
    private Animation<TextureRegion> animacionDerecha;
    private Animation<TextureRegion> animacionIzquierda;
    private float progresoAnimacion = 0;

    public float vidaBase = 100;
    public Player(TiledMap map, World world, Rectangle bounds, GameScreen gameScreen) {
        super(map, world, bounds, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegion("frente"));

        this.gameScreen = gameScreen;

        fdef.filter.categoryBits = 2;
        fdef.filter.maskBits = 1 + 8 + 16 + 32 + 64+128;//(1 = muros, 8 = enemigos, 16 = pinchos, 32 = cofres, 64 = wolf, 128 = bala enemy)
        createBody();

        animacionFrente = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("frente"));
        animacionEspaldas = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("espalda"));
        animacionDerecha = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("derecha"));
        animacionIzquierda = new Animation<TextureRegion>(1 / 4f, ResourceManager.getAtlas("core/assets/personajes/personajePrincipal/mainCharacter.pack").findRegions("izquierda"));

        ultimoEstado = Estados.FRENTE;
        estadoActual = Estados.FRENTE;

        contadorEnemigos = 0;
        vida = vidaBase; // 100 de vidaBase por defecto

    }

    @Override
    public void onContact(Contact contact) {

        if (contact.getFixtureA().getFilterData().categoryBits == 8) {
            vida -= 0.5f;
        } else if (contact.getFixtureB().getFilterData().categoryBits == 8) {
            vida -= 0.5f;
        }

        if (contact.getFixtureB().getFilterData().categoryBits == 16) {
            vida -= 10f;
        } else if (contact.getFixtureA().getFilterData().categoryBits == 16) {
            vida -= 10f;
        }

        if (contact.getFixtureB().getFilterData().categoryBits == 128) {
            vida -= 3f;
        } else if (contact.getFixtureA().getFilterData().categoryBits == 128) {
            vida -= 3f;
        }

        if (contact.getFixtureB().getFilterData().categoryBits == 32) {
            vida = vidaBase;
            fireRate -= 0.005f;
            ((Cofre) contact.getFixtureB().getUserData()).toDestroy = true;

            SoundManager.playCogerCofre();

        } else if (contact.getFixtureA().getFilterData().categoryBits == 32) {
            vida = vidaBase;
            fireRate -= 0.005f;
            ((Cofre) contact.getFixtureA().getUserData()).toDestroy = true;

            SoundManager.playCogerCofre();
        }
    }

    @Override
    public void draw(float dt, Batch batch) {
        TextureRegion tg = getFrame(dt);
        batch.draw(tg, body.getPosition().x - 4 / Constant.PPM, body.getPosition().y - 2 / Constant.PPM, (tg.getRegionWidth() / 4) / Constant.PPM
                , (tg.getRegionHeight() / 4) / Constant.PPM);
    }

    @Override
    public void postDraw(float dt, Batch batch) {
        // Para la barra de vida
        Gdx.gl.glLineWidth(3);
        gameScreen.shapeRenderer.setProjectionMatrix(gameScreen.cameraManager.cam.combined);
        gameScreen.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        gameScreen.shapeRenderer.setColor(Color.RED);
        gameScreen.shapeRenderer.line(
                new Vector2(body.getPosition().x + 0 / Constant.PPM, body.getPosition().y + 20 / Constant.PPM),
                new Vector2(body.getPosition().x + 16 / Constant.PPM, body.getPosition().y + 20 / Constant.PPM));
        gameScreen.shapeRenderer.setColor(Color.WHITE);
        gameScreen.shapeRenderer.line(

                new Vector2(body.getPosition().x - 0 / Constant.PPM, body.getPosition().y + 20 / Constant.PPM),
                new Vector2((body.getPosition().x - 0 / Constant.PPM) + (16 * (vida / vidaBase)) / Constant.PPM, body.getPosition().y + 20 / Constant.PPM));
        gameScreen.shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    @Override
    public void update(float dt) {
        menageInput();
        if (fireLast >= 0) {
            fireLast -= dt;
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

    /**
     * Metodo que aniade los metodos cuando un usuario se mueve o dispara
     */
    private void menageInput() {

        // JUGADOR
        if (inputUp) {
            body.setLinearVelocity(body.getLinearVelocity().x, velocidad);
            estadoActual = Estados.ESPALDAS;
        } else if (inputDown) {
            estadoActual = Estados.FRENTE;
            body.setLinearVelocity(body.getLinearVelocity().x, -velocidad);
        }

        if (inputLeft) {
            estadoActual = Estados.IZQUIERDA;
            body.setLinearVelocity(-velocidad, body.getLinearVelocity().y);
        } else if (inputRight) {
            estadoActual = Estados.DERECHA;
            body.setLinearVelocity(velocidad, body.getLinearVelocity().y);
        }

        if (!inputUp && !inputDown) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        if (!inputLeft && !inputRight) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }

        if (fireLast <= 0) {

            if (inputDispararRight) {
                gameScreen.levelManager.balas.add(
                        new Bala(map, world, new Rectangle(body.getPosition().x * Constant.PPM, body.getPosition().y * Constant.PPM, 10, 10), (short) 0));
                fireLast = fireRate;
                estadoActual = Estados.DERECHA;

            } else if (inputDispararLeft) {
                gameScreen.levelManager.balas.add(
                        new Bala(map, world, new Rectangle(body.getPosition().x * Constant.PPM, body.getPosition().y * Constant.PPM, 10, 10), (short) 1));
                fireLast = fireRate;
                estadoActual = Estados.IZQUIERDA;

            } else if (inputDispararUp) {
                gameScreen.levelManager.balas.add(
                        new Bala(map, world, new Rectangle(body.getPosition().x * Constant.PPM, body.getPosition().y * Constant.PPM, 10, 10), (short) 2));
                fireLast = fireRate;
                estadoActual = Estados.ESPALDAS;

            } else if (inputDispararDown) {
                gameScreen.levelManager.balas.add(
                        new Bala(map, world, new Rectangle(body.getPosition().x * Constant.PPM, body.getPosition().y * Constant.PPM, 10, 10), (short) 3));
                fireLast = fireRate;
                estadoActual = Estados.FRENTE;
            }
        }
    }

    //Estados
    private enum Estados {
        FRENTE, ESPALDAS, IZQUIERDA, DERECHA
    }


}