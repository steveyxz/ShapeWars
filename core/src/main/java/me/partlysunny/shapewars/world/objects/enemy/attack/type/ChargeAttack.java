package me.partlysunny.shapewars.world.objects.enemy.attack.type;

public class ChargeAttack extends BasicAttack {

    @Override
    public float maxDistance() {
        return 50;
    }

    @Override
    protected float dashFactor() {
        return 0.4f;
    }

    @Override
    protected int getDamage() {
        return 10;
    }

    @Override
    protected float dashDuration() {
        return 4;
    }

    @Override
    public float cooldown() {
        return 5;
    }
}
