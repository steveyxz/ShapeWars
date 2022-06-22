package me.partlysunny.shapewars.world.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public interface GameObject {

    Entity createEntity(PooledEngine w, float originalX, float originalY);

}
