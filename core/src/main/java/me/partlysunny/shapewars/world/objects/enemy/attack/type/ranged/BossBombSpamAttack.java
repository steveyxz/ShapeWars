package me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged;

public class BossBombSpamAttack extends BombAttack {

    @Override
    public float maxDistance() {
        return 100;
    }

    @Override
    protected int damage() {
        return 50;
    }

    @Override
    protected int speed() {
        return 1200;
    }

    @Override
    public float cooldown() {
        return 1;
    }
}
