package me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged;

public class SniperBlasterAttack extends BasicBlasterAttack {

    @Override
    public float maxDistance() {
        return 120;
    }

    @Override
    protected int getDamage() {
        return 20;
    }

    @Override
    public float cooldown() {
        return 4f;
    }
}
