package me.partlysunny.shapewars.world.objects.enemy.type;

import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

public class RoyalGuardEnemy extends BasicEnemy {

    @Override
    protected float getHealth() {
        return 50;
    }

    @Override
    protected String getTexture() {
        return "royalGuardEnemy";
    }

    @Override
    protected float getMaxSpeed() {
        return 10;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.ROYAL_GUARD_ATTACK;
    }

    @Override
    protected float viewRange() {
        return 500;
    }
}
