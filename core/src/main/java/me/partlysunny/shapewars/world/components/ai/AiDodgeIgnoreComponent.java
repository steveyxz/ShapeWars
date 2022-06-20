package me.partlysunny.shapewars.world.components.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class AiDodgeIgnoreComponent implements Component, Pool.Poolable {

    private boolean active = true;

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void reset() {
        active = true;
    }
}
