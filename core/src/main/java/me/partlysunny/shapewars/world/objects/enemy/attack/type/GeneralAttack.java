package me.partlysunny.shapewars.world.objects.enemy.attack.type;

public class GeneralAttack extends BasicAttack {

    @Override
    public float maxDistance() {
        return 30;
    }

    @Override
    protected float dashFactor() {
        return 1.5f;
    }

    @Override
    protected int getDamage() {
        return 30;
    }

    @Override
    protected float dashDuration() {
        return 0.5f;
    }

    @Override
    public float cooldown() {
        return 2;
    }
}
