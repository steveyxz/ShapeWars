package me.partlysunny.shapewars.world.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TintComponent implements Component, Pool.Poolable {

    private final Vector3 tint = new Vector3(1, 1, 1);
    private float alpha = 1;

    public void setTint(float r, float g, float b, float a) {
        tint.set(r, g, b);
        this.alpha = a;
    }

    public Vector3 tint() {
        return tint;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void reset() {
        tint.set(0, 0, 0);
        this.alpha = 0;
    }

    public float alpha() {
        return alpha;
    }
}
