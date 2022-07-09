package me.partlysunny.shapewars.world.systems.mechanics.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.enemy.loot.LootItemComponent;

public class LootItemUpdaterSystem extends IteratingSystem {
    public LootItemUpdaterSystem() {
        super(Family.all(LootItemComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Mappers.lootItemMapper.get(entity).update(deltaTime);
    }
}
