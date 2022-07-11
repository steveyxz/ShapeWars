package me.partlysunny.shapewars.world.objects.enemy.type;

import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

public class SniperEnemy extends BlasterEnemy {

    @Override
    protected float getHealth() {
        return 60;
    }

    @Override
    protected String getTexture() {
        return "sniperEnemy";
    }

    @Override
    protected float getMaxSpeed() {
        return 10;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.SNIPER_ATTACK;
    }

    @Override
    protected float viewRange() {
        return 75;
    }
}
