package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.Entity;

public abstract class EnemyAttack {

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
