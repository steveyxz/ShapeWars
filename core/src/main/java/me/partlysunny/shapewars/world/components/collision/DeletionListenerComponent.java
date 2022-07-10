package me.partlysunny.shapewars.world.components.collision;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.function.Consumer;

public class DeletionListenerComponent implements Component, Pool.Poolable {

    private Consumer<Entity> onDelete = null;

    public Consumer<Entity> onDelete() {
        return onDelete;
    }

    public void init(Consumer<Entity> onDelete) {
        this.onDelete = onDelete;
    }

    @Override
    public void reset() {
        onDelete = null;
    }
}
