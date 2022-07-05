package me.partlysunny.shapewars.world.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.util.utilities.LateRemover;

import java.util.ArrayList;
import java.util.List;

public class PlayerMeleeAttackComponent implements Component, Pool.Poolable {

    private final List<Entity> hasHit = new ArrayList<>();
    private float lifeRemaining = 0;
    private Entity parent = null;
    private float xMovement = 0;
    private float yMovement = 0;
    private int damage = 0;

    public void init(float life, Entity parent, float angle, float vel, int damage) {
        this.lifeRemaining = life;
        this.parent = parent;
        this.damage = damage;
        xMovement = MathUtils.cos(angle * MathUtils.degreesToRadians) * vel;
        yMovement = MathUtils.sin(angle * MathUtils.degreesToRadians) * vel;
    }

    public void update(float delta) {
        lifeRemaining -= delta;
        if (lifeRemaining <= 0) {
            LateRemover.tagToRemove(parent);
        }
    }

    public float xMovement() {
        return xMovement;
    }

    public float yMovement() {
        return yMovement;
    }

    public float lifeRemaining() {
        return lifeRemaining;
    }

    public void setLifeRemaining(float lifeRemaining) {
        this.lifeRemaining = lifeRemaining;
    }

    public Entity parent() {
        return parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public void hit(Entity e) {
        hasHit.add(e);
    }

    public boolean canHit(Entity e) {
        return !hasHit.contains(e);
    }

    public int damage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void reset() {
        lifeRemaining = 0;
        parent = null;
        xMovement = 0;
        yMovement = 0;
        damage = 0;
        hasHit.clear();
    }
}
