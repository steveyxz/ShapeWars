package me.partlysunny.shapewars.world.components.mechanics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;

public class BombComponent implements Component, Pool.Poolable {

    private int damage = 0;
    private BulletRestrictions restrictions = BulletRestrictions.BOTH;

    public void init(int damage, BulletRestrictions restrictions) {
        this.damage = damage;
        this.restrictions = restrictions;
    }

    public BulletRestrictions restrictions() {
        return restrictions;
    }

    public int damage() {
        return damage;
    }

    @Override
    public void reset() {
        damage = 0;
        restrictions = BulletRestrictions.BOTH;
    }
}
