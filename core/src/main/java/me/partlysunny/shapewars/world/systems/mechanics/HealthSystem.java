package me.partlysunny.shapewars.world.systems.mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;

public class HealthSystem extends IteratingSystem {

    public HealthSystem() {
        super(Family.all(HealthComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent health = Mappers.healthMapper.get(entity);
        if (health.health() <= 0) {
            LateRemover.tagToRemove(entity);
            if (Mappers.lootMapper.has(entity)) {
                Mappers.lootMapper.get(entity).table().dropTableAt(entity);
            }
        }
    }
}
