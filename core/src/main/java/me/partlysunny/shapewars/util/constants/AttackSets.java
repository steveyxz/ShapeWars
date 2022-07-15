package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.melee.*;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.misc.FastSummonMinionsAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.misc.SummonMinionsAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged.BasicBlasterAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged.BombAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged.CommanderBlasterAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged.SniperBlasterAttack;

public final class AttackSets {

    public static final EnemyAttackSelector BASIC_ATTACK = new EnemyAttackSelector().add(new BasicAttack());
    public static final EnemyAttackSelector FAST_TANKY_ATTACK = new EnemyAttackSelector().add(new SpeedyBasicAttack());
    public static final EnemyAttackSelector BASIC_RANGED = new EnemyAttackSelector().add(new BasicBlasterAttack());
    public static final EnemyAttackSelector BASIC_COMMANDER = new EnemyAttackSelector().add(new CommanderBlasterAttack()).add(new SummonMinionsAttack(1, 3, 5, 10, "basic"));
    public static final EnemyAttackSelector MEGA_TANKY_ATTACK = new EnemyAttackSelector().add(new ChargeAttack()).add(new SpeedyBasicAttack());
    public static final EnemyAttackSelector BASIC_BOMBER = new EnemyAttackSelector().add(new BombAttack());
    public static final EnemyAttackSelector SNIPER_ATTACK = new EnemyAttackSelector().add(new SniperBlasterAttack());
    public static final EnemyAttackSelector BASIC_GENERAL = new EnemyAttackSelector().add(new GeneralAttack()).add(new SummonMinionsAttack(1, 1, 6, 10, "sniper"));
    public static final EnemyAttackSelector WEAK_BASIC_ATTACK = new EnemyAttackSelector().add(new WeakBasicAttack());
    public static final EnemyAttackSelector SWARMER = new EnemyAttackSelector().add(new CommanderBlasterAttack()).add(new FastSummonMinionsAttack(4, 8, 5, 10, "swarm"));
    public static final EnemyAttackSelector HEX_ATTACK = new EnemyAttackSelector().add(new HexAttack());
}
