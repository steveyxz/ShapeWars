package me.partlysunny.shapewars.world.objects.general;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class HealthBarPool extends Pool<HealthBarActor> {

    public HealthBarPool() {
        super();
    }

    public HealthBarPool(int initialCapacity) {
        super(initialCapacity);
    }

    public HealthBarPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    @Override
    protected HealthBarActor newObject() {
        return new HealthBarActor();
    }

    public HealthBarActor obtainHealthBar(Entity parent) {
        HealthBarActor healthBarActor = newObject();
        healthBarActor.init(parent);
        return healthBarActor;
    }
}
