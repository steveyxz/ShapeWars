package me.partlysunny.shapewars.world.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerCameraFollowComponent implements Component, Pool.Poolable {

    private float speed = 10;

    public float speed() {
        return speed;
    }

    public void init(float speed) {
        this.speed = speed;
    }

    @Override
    public void reset() {
        speed = 3;
    }
}
