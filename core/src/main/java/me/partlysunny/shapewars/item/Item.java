package me.partlysunny.shapewars.item;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public interface Item {

    Entity buildEntity(PooledEngine engine);

    String name();

    String description();

    String texture();

    float renderSizeX()
            ;

    float renderSizeY();
}
