package me.partlysunny.shapewars.world.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.util.utilities.LateRemover;

public class PlayerMeleeAttackComponent implements Component, Pool.Poolable {

    private float lifeRemaining = 0;
    private Entity parent = null;
    private float xMovement = 0;
    private float yMovement = 0;

    public void init(float life, Entity parent, float angle, float vel) {
        this.lifeRemaining = life;
        this.parent = parent;
        System.out.println(angle);
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

    @Override
    public void reset() {
        lifeRemaining = 0;
        parent = null;
        xMovement = 0;
        yMovement = 0;
    }
}
