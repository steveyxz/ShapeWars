package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.objects.EntityManager;

public class RockEntity extends SteerableAdapter<Vector2> implements ObstacleEntity {

    private float x;
    private float y;
    private float width;
    private float height;
    private float angle;

    public RockEntity() {
        this.x = Util.getRandomBetween(-200, 200);
        this.y = Util.getRandomBetween(-200, 200);
        this.width = Util.getRandomBetween(7, 10);
        this.height = Util.getRandomBetween(7, 10);
        this.angle = Util.getRandomBetween(0, 360);
    }

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
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public float angle() {
        return angle;
    }

}
