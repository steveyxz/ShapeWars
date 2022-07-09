package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.Entity;

public interface EnemyAttack {

    float maxDistance();

    void attack(Entity enemyEntity, Entity player);

    float cooldown();

}
