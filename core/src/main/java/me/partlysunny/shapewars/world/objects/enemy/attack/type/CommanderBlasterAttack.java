package me.partlysunny.shapewars.world.objects.enemy.attack.type;

public class CommanderBlasterAttack extends BasicBlasterAttack {

    @Override
    public float cooldown() {
        return 0.5f;
    }

    @Override
    protected int getDamage() {
        return 6;
    }
}
