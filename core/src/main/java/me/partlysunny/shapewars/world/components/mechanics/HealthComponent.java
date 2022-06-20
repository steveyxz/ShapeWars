package me.partlysunny.shapewars.world.components.mechanics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {

    private float health = 0;
    private float maxHealth = 0;

    public void init(float health) {
        this.maxHealth = health;
        this.health = health;
    }

    public float health() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void addHealth(float health) {
        setHealth(health() + health);
    }

    @Override
    public void reset() {
        health = 0;
    }
}
