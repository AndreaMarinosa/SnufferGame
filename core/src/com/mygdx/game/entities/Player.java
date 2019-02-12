package com.mygdx.game.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DinamicBody {

    public Player(TiledMap map, World world, Rectangle bounds) {
        super(map, world, bounds);

    }
}
