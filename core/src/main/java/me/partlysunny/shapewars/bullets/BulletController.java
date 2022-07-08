package me.partlysunny.shapewars.bullets;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public interface BulletController extends ContactListener {

    void fire(Entity shooter, float damage);

    void updateBullet(BulletComponent component, float delta);

    @Override
    void preSolve(Contact contact, Manifold oldManifold);

    @Override
    void postSolve(Contact contact, ContactImpulse impulse);
}
