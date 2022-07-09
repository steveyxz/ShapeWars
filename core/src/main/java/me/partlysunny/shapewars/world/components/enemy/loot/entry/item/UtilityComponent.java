package me.partlysunny.shapewars.world.components.enemy.loot.entry.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class UtilityComponent implements Component, Pool.Poolable {

    private String type;

    public void init(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    @Override
    public void reset() {
        type = null;
    }
}
