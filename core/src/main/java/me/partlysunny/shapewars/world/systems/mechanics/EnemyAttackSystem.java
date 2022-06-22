package me.partlysunny.shapewars.world.systems.mechanics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.world.components.ai.EnemyAttackComponent;

public class EnemyAttackSystem extends IteratingSystem {

    private ComponentMapper<EnemyAttackComponent> attackMapper = ComponentMapper.getFor(EnemyAttackComponent.class);

    public EnemyAttackSystem() {
        super(Family.all(EnemyAttackComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyAttackComponent attack = attackMapper.get(entity);
        attack.update(deltaTime);
    }
}
