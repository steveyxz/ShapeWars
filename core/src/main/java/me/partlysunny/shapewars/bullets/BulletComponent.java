package me.partlysunny.shapewars.bullets;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;

public class BulletComponent implements Component, Pool.Poolable {

    private float damage = 0;
    private Entity shooter = null;
    private BulletController controller = null;
    private BulletRestrictions restrictions = BulletRestrictions.BOTH;
    private float life = 0;
    private Entity bulletEntity = null;

    public void init(Entity shooter, Entity bulletEntity, float damage, BulletController controller, BulletRestrictions restrictions) {
        this.damage = damage;
        this.bulletEntity = bulletEntity;
        this.shooter = shooter;
        this.controller = controller;
        this.restrictions = restrictions;
        this.life = 5;
    }

    public void init(Entity shooter, Entity bulletEntity, float damage, BulletController controller, BulletRestrictions restrictions, float life) {
        this.damage = damage;
        this.bulletEntity = bulletEntity;
        this.shooter = shooter;
        this.controller = controller;
        this.restrictions = restrictions;
        this.life = life;
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

    public BulletRestrictions restrictions() {
        return restrictions;
    }

    public void setRestrictions(BulletRestrictions restrictions) {
        this.restrictions = restrictions;
    }

    public float life() {
        return life;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public void update(float delta) {
        controller.updateBullet(this, delta);
        if (life != -1) {
            life -= delta;
            if (life < 0) {
                VisualEffectManager.getEffect("bulletFade").playEffect(bulletEntity);
                life = -1;
            }
        }
    }

    @Override
    public void reset() {
        damage = 0;
        life = 0;
        shooter = null;
        controller = null;
        restrictions = BulletRestrictions.BOTH;
        bulletEntity = null;
    }
}
