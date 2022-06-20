package me.partlysunny.shapewars.util.classes;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public class ContactDispatcher implements ContactListener {

    private static final List<ContactListener> listeners = new ArrayList<>();

    public static void registerListener(ContactListener listener) {
        listeners.add(listener);
    }

    public static void init(World w) {
        w.setContactListener(new ContactDispatcher());
    }

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener l : listeners) {
            l.beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener l : listeners) {
            l.endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener l : listeners) {
            l.preSolve(contact, oldManifold);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener l : listeners) {
            l.postSolve(contact, impulse);
        }
    }
}
