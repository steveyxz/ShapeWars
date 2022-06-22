package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.BasicAttack;

public final class AttackSets {

    public static final EnemyAttackSelector BASIC_ATTACK = new EnemyAttackSelector().add(new BasicAttack(1));

}
