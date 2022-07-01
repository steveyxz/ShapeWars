package me.partlysunny.shapewars.world.systems.mechanics.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyMeleeDamageComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyStateComponent;

public class EnemyStateSystem extends IteratingSystem {

    public EnemyStateSystem() {
        super(Family.all(EnemyStateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyStateComponent attack = Mappers.enemyStateMapper.get(entity);
        attack.update(deltaTime);
    }

}
