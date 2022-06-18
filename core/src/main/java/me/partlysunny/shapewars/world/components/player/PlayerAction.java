package me.partlysunny.shapewars.world.components.player;

import com.badlogic.gdx.Input;

public enum PlayerAction {

    UP(Input.Keys.W),
    LEFT(Input.Keys.A),
    DOWN(Input.Keys.S),
    RIGHT(Input.Keys.D),
    ATTACK(Input.Keys.SPACE),
    ;

    private final int defaultKey;

    PlayerAction(int defaultKey) {
        this.defaultKey = defaultKey;
    }

    public int defaultKey() {
        return defaultKey;
    }
}
