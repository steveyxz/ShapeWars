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
        return 60;
    }

    @Override
    protected float dashDuration() {
        return 1f;
    }

    @Override
    public float cooldown() {
        return 4;
    }
}
