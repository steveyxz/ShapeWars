package me.partlysunny.shapewars.world.objects.enemy.attack.type;

public class SpeedyBasicAttack extends BasicAttack {
    public SpeedyBasicAttack(int weight) {
        super(weight);
    }

    @Override
    public float cooldown() {
        return 0.2f;
    }

    @Override
    protected float maxDistance() {
        return 12;
    }
}
