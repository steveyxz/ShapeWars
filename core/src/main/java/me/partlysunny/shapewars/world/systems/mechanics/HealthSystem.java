package me.partlysunny.shapewars.world.systems.mechanics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;

public class HealthSystem extends IteratingSystem {

    private ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    public HealthSystem() {
        super(Family.all(HealthComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent health = healthMapper.get(entity);
        if (health.health() <= 0) {
            LateRemover.tagToRemove(entity);
        }
    }
}
