package me.partlysunny.shapewars.world.objects.enemy.attack.type.misc;

public class FastSummonMinionsAttack extends SummonMinionsAttack {
    public FastSummonMinionsAttack(int min, int max, float closestEnemy, float furthestEnemy, String enemyTypeToSummon) {
        super(min, max, closestEnemy, furthestEnemy, enemyTypeToSummon);
    }

    @Override
    public float cooldown() {
        return 2f;
    }
}
