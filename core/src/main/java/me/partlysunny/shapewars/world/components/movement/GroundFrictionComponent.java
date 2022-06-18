package me.partlysunny.shapewars.world.components.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GroundFrictionComponent implements Component, Pool.Poolable {

    private float friction = 1f;

    public GroundFrictionComponent() {
    }

    public float friction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    @Override
    public void reset() {
        friction = 1;
    }
}
