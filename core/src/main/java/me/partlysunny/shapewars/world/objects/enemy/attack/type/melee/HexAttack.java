package me.partlysunny.shapewars.world.objects.enemy.attack.type.melee;

public class HexAttack extends BasicAttack {

    @Override
    public float maxDistance() {
        return 25;
    }

    @Override
    protected float dashFactor() {
        return 1.2f;
    }

    @Override
    protected int getDamage() {
        return 50;
    }

    @Override
    protected float dashDuration() {
        return 1.5f;
    }

    @Override
    public float cooldown() {
        return 3;
    }
}
