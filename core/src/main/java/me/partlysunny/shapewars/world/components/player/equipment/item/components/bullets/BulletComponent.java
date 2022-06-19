package me.partlysunny.shapewars.world.components.player.equipment.item.components.bullets;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class BulletComponent implements Component, Pool.Poolable {

    private float damage = 0;
    private Entity shooter = null;
    private BulletController controller = null;

    public void init(Entity shooter, float damage, BulletController controller) {
        this.damage = damage;
        this.shooter = shooter;
        this.controller = controller;
    }

    public float damage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public Entity shooter() {
        return shooter;
    }

    public void setShooter(Entity shooter) {
        this.shooter = shooter;
    }

    public BulletController controller() {
        return controller;
    }

    public void setController(BulletController controller) {
        this.controller = controller;
    }

    public void update(float delta) {
        controller.updateBullet(this, delta);
    }

    @Override
    public void reset() {
        damage = 0;
        shooter = null;
    }
}
