package me.partlysunny.shapewars.world.components.player.state;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class StateComponent implements Component, Pool.Poolable {

    public float time = 0.0f;
    public boolean isLooping = true;
    private State state;

    public void init(State s) {
        this.state = s;
    }

    @Override
    public void reset() {
        state = State.PASSIVE;
        isLooping = true;
        time = 0.0f;
    }

    public float time() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public State state() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int get() {
        return state.value();
    }
}
