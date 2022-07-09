package me.partlysunny.shapewars.world.systems.mechanics.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.enemy.EnemyAttackComponent;

public class EnemyAttackSystem extends IteratingSystem {

    public EnemyAttackSystem() {
        super(Family.all(EnemyAttackComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyAttackComponent attack = Mappers.enemyAttackMapper.get(entity);
        attack.update(deltaTime);
    }
}
