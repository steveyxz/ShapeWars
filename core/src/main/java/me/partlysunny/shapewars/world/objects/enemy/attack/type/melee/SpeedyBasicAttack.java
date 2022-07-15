package me.partlysunny.shapewars.world.objects.enemy.attack.type.melee;

public class SpeedyBasicAttack extends BasicAttack {
    @Override
    public float cooldown() {
        return 0.6f;
    }

    @Override
    public float maxDistance() {
        return 12;
    }
}
