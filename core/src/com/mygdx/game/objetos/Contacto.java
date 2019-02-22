package com.mygdx.game.objetos;

import com.badlogic.gdx.physics.box2d.Contact;

/**
 * Created by DAM on 22/02/2019.
 */
public abstract class Contacto {
    public abstract void onContact(Contact contact);
}
