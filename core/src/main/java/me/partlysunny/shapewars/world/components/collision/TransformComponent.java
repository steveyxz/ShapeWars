package me.partlysunny.shapewars.world.components.collision;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public boolean isHidden = false;

    public void init(float width, float height) {
        this.scale.set(width, height);
    }

    @Override
    public void reset() {
        position.set(0, 0, 0);
        scale.set(1, 1);
        rotation = 0;
        isHidden = false;
    }
}
