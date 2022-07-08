package me.partlysunny.shapewars.world.components.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class EnemySpawnIndicatorComponent implements Component, Pool.Poolable {

    private Entity indicator;

    public void init(Entity indicator) {
        this.indicator = indicator;
    }

    @Override
    public void reset() {
        indicator = null;
    }
}
