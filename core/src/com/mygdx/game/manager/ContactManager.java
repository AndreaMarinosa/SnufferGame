package com.mygdx.game.manager;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.DinamicBody;

public class ContactManager implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() instanceof DinamicBody){
            ((DinamicBody)contact.getFixtureA().getUserData()).onContact(contact);

        }
        if (contact.getFixtureB().getUserData() instanceof DinamicBody){
            ((DinamicBody)contact.getFixtureB().getUserData()).onContact(contact);

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
