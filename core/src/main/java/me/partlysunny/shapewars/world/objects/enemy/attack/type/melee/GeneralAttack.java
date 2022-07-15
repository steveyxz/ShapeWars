package me.partlysunny.shapewars.world.objects.enemy.attack.type.melee;

public class GeneralAttack extends BasicAttack {

    @Override
    public float maxDistance() {
        return 30;
    }

    @Override
    protected float dashFactor() {
        return 1f;
    }

    @Override
    protected int getDamage() {
        return 50;
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
