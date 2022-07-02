package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.BasicAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.BasicBlasterAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.SpeedyBasicAttack;

public final class AttackSets {

    public static final EnemyAttackSelector BASIC_ATTACK = new EnemyAttackSelector().add(new BasicAttack(1));
    public static final EnemyAttackSelector FAST_TANKY_ATTACK = new EnemyAttackSelector().add(new SpeedyBasicAttack(1));
    public static final EnemyAttackSelector BASIC_RANGED = new EnemyAttackSelector().add(new BasicBlasterAttack(1));
}
