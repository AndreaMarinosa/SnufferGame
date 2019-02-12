package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DinamicBody {

    public boolean inputUp = false;
    public boolean inputDown = false;
    public boolean inputLeft = false;
    public boolean inputRight = false;
    public boolean inputFire = false;

    public Player(TiledMap map, World world, Rectangle bounds) {
        super(map, world, bounds);
        fdef.filter.categoryBits=2;
        fdef.filter.maskBits=1;
        createBody();
    }

    @Override
    public void onContact(Contact contact) {
        System.out.println("en player");
    }

    @Override
    public void draw(float dt, Batch batch) {

    }

    @Override
    public void postDraw(float dt, Batch batch) {

    }

    @Override
    public void update(float dt) {
        menageInput();
    }


    private void menageInput() {

        if(inputUp){
            body.setLinearVelocity(body.getLinearVelocity().x,200);
        }else if(inputDown){
            body.setLinearVelocity(body.getLinearVelocity().x,-200);
        }


        if(inputLeft){
            body.setLinearVelocity(-200,body.getLinearVelocity().y);
        }else if(inputRight){
            body.setLinearVelocity(200,body.getLinearVelocity().y);
        }

        if(!inputUp && !inputDown){
            body.setLinearVelocity(body.getLinearVelocity().x,0);
        }
        if(!inputLeft && !inputRight){
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }

    }


}
