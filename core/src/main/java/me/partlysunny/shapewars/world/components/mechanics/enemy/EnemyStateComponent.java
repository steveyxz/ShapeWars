package me.partlysunny.shapewars.world.components.mechanics.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyStateComponent implements Component, Pool.Poolable {

    private EnemyState state = EnemyState.PURSUING;
    private EnemyState goingTo = EnemyState.PURSUING;
    private float countdown = -1;

    public EnemyState state() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public void setState(EnemyState state, float in) {
        goingTo = state;
        countdown = in;
    }

    public void update(float delta) {
        if (countdown != -1) {
            countdown -= delta;
            if (countdown < 0) {
                state = goingTo;
                countdown = -1;
            }
        }
    }

    @Override
    public void reset() {
        state = EnemyState.PURSUING;
    }
}
