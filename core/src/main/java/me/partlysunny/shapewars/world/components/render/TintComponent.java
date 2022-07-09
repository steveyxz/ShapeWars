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
        validate();
    }

    private void validate() {
        if (alpha < 0) alpha = 0;
        if (tint.x < 0) tint.x = 0;
        if (tint.y < 0) tint.y = 0;
        if (tint.z < 0) tint.z = 0;
        if (alpha > 1) alpha = 1;
        if (tint.x > 1) tint.x = 1;
        if (tint.y > 1) tint.y = 1;
        if (tint.z > 1) tint.z = 1;
    }

    public Vector3 tint() {
        return tint;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void reset() {
        tint.set(1, 1, 1);
        this.alpha = 1;
    }

    public float alpha() {
        return alpha;
    }
}
