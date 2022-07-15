package me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.enemy.EnemyState;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;

public class BossAroundShootAttack implements EnemyAttack {
    private final Vector2 vec = new Vector2();

    @Override
    public float maxDistance() {
        return 200;
    }

    protected int getDamage() {
        return 20;
    }

    @Override
    public void attack(Entity enemyEntity, Entity player) {
        for (int i = 0; i < 40; i++) {
            shoot(9 * i, enemyEntity);
        }
    }

    private void shoot(float angle, Entity enemyEntity) {
        TransformComponent enemyPos = Mappers.transformMapper.get(enemyEntity);

        Util.angleToVector(vec, angle);
        vec.scl(10);
        enemyPos.rotation = angle;

        Controllers.ENEMY_BLASTER.fire(enemyEntity, getDamage());
        SoundEffectManager.play("enemyBlasterShoot", 0.2f);

        Mappers.enemyStateMapper.get(enemyEntity).setState(EnemyState.ATTACKING);
        Mappers.enemyStateMapper.get(enemyEntity).setState(EnemyState.MOVING, 0.5f);
    }

    @Override
    public float cooldown() {
        return 2f;
    }
}
