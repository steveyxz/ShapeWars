package me.partlysunny.shapewars.world.objects.enemy.attack.type;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.util.factories.BombFactory;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;

public class BombAttack implements EnemyAttack {
    @Override
    public float maxDistance() {
        return 60;
    }

    @Override
    public void attack(Entity enemyEntity, Entity player) {
        BombFactory.getInstance().generateBomb(enemyEntity, 2, 5, BulletRestrictions.ONLY_PLAYERS, 15, 300, "enemyThrow", "enemyBomb", "basicExplode");
    }

    @Override
    public float cooldown() {
        return 3;
    }
}
