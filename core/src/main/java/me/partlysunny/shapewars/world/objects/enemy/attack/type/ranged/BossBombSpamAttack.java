package me.partlysunny.shapewars.world.objects.enemy.attack.type.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Timer;

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
