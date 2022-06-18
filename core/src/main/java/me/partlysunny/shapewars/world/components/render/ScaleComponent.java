package me.partlysunny.shapewars.world.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ScaleComponent implements Component, Pool.Poolable {

    private float width = 0;
    private float height = 0;

    public ScaleComponent() {
    }

    public ScaleComponent(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float width() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float height() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void init(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void reset() {
        this.width = 0;
        this.height = 0;
    }
}
