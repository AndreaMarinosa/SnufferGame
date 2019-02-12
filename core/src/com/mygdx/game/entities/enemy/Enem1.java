package com.mygdx.game.entities.enemi;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Enemy;

public class Enem1 extends Enemy {
    public Enem1(TiledMap map, World world, Rectangle bounds) {
        super(map, world, bounds);
    }

    @Override
    public void onHitPlayer() {

    }

    @Override
    public void onHitWall() {

    }
}
