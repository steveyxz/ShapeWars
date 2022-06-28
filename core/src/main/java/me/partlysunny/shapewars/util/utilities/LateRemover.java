package me.partlysunny.shapewars.util.utilities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.World;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.WallComponent;

import java.util.ArrayList;
import java.util.List;

public class LateRemover {

    private static final List<Entity> toRemove = new ArrayList<>();
    private static final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);
    private static final List<Entity> tempRemove = new ArrayList<>();

    public static void tagToRemove(Entity e) {
        toRemove.add(e);
    }

    public static void process() {
        tempRemove.clear();
        PooledEngine world = InGameScreen.world.gameWorld();
        World physics = InGameScreen.world.physicsWorld();
        for (Entity e : world.getEntitiesFor(Family.all().get())) {
            if (toRemove.contains(e)) {
                if (bodyMapper.has(e)) {
                    physics.destroyBody(bodyMapper.get(e).rigidBody());
                }
                world.removeEntity(e);
                InGameScreen.levelManager.entityDestroyed(e);
                tempRemove.add(e);
            }
        }
        for (Entity e : tempRemove) {
            toRemove.remove(e);
        }
    }

}
