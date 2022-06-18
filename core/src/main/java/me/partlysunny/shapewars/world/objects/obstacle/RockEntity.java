package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.util.Util;
import me.partlysunny.shapewars.world.objects.EntityManager;
import me.partlysunny.shapewars.world.objects.GameObject;

public class RockEntity implements ObstacleEntity, GameObject {
    @Override
    public void createEntity(PooledEngine w) {
        EntityManager.addObstacle(w, this);
    }

    @Override
    public String getTexture() {
        return "rock";
    }

    @Override
    public float x() {
        return Util.getRandomBetween(-200, 200);
    }

    @Override
    public float y() {
        return Util.getRandomBetween(-200, 200);
    }

    @Override
    public float width() {
        return Util.getRandomBetween(7, 10);
    }

    @Override
    public float height() {
        return width();
    }

    @Override
    public float angle() {
        return Util.getRandomBetween(0, 360);
    }
}
