package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;

public abstract class EnemyAttack {

    protected final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    protected final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);
    private final int weight;

    protected EnemyAttack(int weight) {
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }

    protected abstract float maxDistance();

    protected abstract void attack(Entity enemyEntity, Entity player);

    public abstract float cooldown();

}
