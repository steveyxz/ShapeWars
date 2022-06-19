package me.partlysunny.shapewars.world.components.player.equipment.item.components.bullets;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.ContactListener;

public interface BulletController extends ContactListener {

    void fire(Entity player, float damage);

    void updateBullet(BulletComponent component, float delta);

}
