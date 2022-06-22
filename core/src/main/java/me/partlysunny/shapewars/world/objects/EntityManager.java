package me.partlysunny.shapewars.world.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public class EntityManager {

    private final PooledEngine w;

    public EntityManager(PooledEngine w) {
        this.w = w;
    }

    public PooledEngine w() {
        return w;
    }

    public Entity registerEntity(GameObject g, float originalX, float originalY) {
        return g.createEntity(w, originalX, originalY);
    }
}
