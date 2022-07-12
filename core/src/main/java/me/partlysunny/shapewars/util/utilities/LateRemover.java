package me.partlysunny.shapewars.util.utilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.World;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.Mappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LateRemover {

    private static final List<Entity> toRemove = new ArrayList<>();
    private static final List<Entity> tempRemove = new ArrayList<>();

    public static void tagToRemove(Entity e) {
        toRemove.add(e);
    }

    public static void process() {
        PooledEngine world = InGameScreen.world.gameWorld();
        World physics = InGameScreen.world.physicsWorld();
        tempRemove.clear();
        tempRemove.addAll(toRemove);
        for (Entity e : tempRemove) {
            if (Mappers.bodyMapper.has(e)) {
                physics.destroyBody(Mappers.bodyMapper.get(e).rigidBody());
            }
            if (Mappers.deleteListenerMapper.has(e)) {
                Mappers.deleteListenerMapper.get(e).onDelete().accept(e);
            }
            world.removeEntity(e);
            InGameScreen.levelManager.entityDestroyed(e);
        }
        toRemove.clear();
    }

}
