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
        return Math.min(maxHealth, health);
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float maxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void addHealth(float health) {
        setHealth(health() + health);
    }

    @Override
    public void reset() {
        maxHealth = 0;
        health = 0;
    }
}
