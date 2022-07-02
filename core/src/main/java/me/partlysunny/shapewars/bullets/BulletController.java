package me.partlysunny.shapewars.bullets;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.ContactListener;

public interface BulletController extends ContactListener {

    void fire(Entity shooter, float damage);

    void updateBullet(BulletComponent component, float delta);

}
