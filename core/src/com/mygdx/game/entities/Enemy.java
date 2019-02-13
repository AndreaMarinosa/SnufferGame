package com.mygdx.game.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Enemy extends DinamicBody {
    public Enemy(TiledMap map, World world, Rectangle bounds) {
        super(map, world, bounds, ResourceManager.getAtlas("").findRegion(""));
    }

    public abstract void onHitPlayer();

    public abstract void onHitWall();

}
