package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.*;

public final class AttackSets {

    public static final EnemyAttackSelector BASIC_ATTACK = new EnemyAttackSelector().add(new BasicAttack());
    public static final EnemyAttackSelector FAST_TANKY_ATTACK = new EnemyAttackSelector().add(new SpeedyBasicAttack());
    public static final EnemyAttackSelector BASIC_RANGED = new EnemyAttackSelector().add(new BasicBlasterAttack());
    public static final EnemyAttackSelector BASIC_COMMANDER = new EnemyAttackSelector().add(new CommanderBlasterAttack()).add(new SummonMinionsAttack(1, 3, 5, 10, "basic"));
    public static final EnemyAttackSelector MEGA_TANKY_ATTACK = new EnemyAttackSelector().add(new ChargeAttack()).add(new SpeedyBasicAttack());
    public static final EnemyAttackSelector BASIC_BOMBER = new EnemyAttackSelector().add(new BombAttack());
    public static final EnemyAttackSelector SNIPER_ATTACK = new EnemyAttackSelector().add(new SniperBlasterAttack());
    public static final EnemyAttackSelector BASIC_GENERAL = new EnemyAttackSelector().add(new GeneralAttack()).add(new SummonMinionsAttack(1, 1, 6, 10, "sniper"));
}
