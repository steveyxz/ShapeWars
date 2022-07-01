package me.partlysunny.shapewars.world.components.mechanics.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyMeleeDamageComponent implements Component, Pool.Poolable {

    private int damage = 0;

    public void init(int damage) {
        this.damage = damage;
    }

    public int damage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void reset() {
        damage = 0;
    }
}
