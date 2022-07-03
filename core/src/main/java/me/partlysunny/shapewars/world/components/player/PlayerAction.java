package me.partlysunny.shapewars.world.components.player;

import com.badlogic.gdx.Input;

public enum PlayerAction {

    UP(Input.Keys.W),
    LEFT(Input.Keys.A),
    DOWN(Input.Keys.S),
    RIGHT(Input.Keys.D),
    ATTACK(Input.Keys.SPACE),
    OPEN_INVENTORY(Input.Keys.E),
    WEAPON_SLOT_1(Input.Keys.NUM_1),
    WEAPON_SLOT_2(Input.Keys.NUM_2);

    private final int defaultKey;

    PlayerAction(int defaultKey) {
        this.defaultKey = defaultKey;
    }

    public int defaultKey() {
        return defaultKey;
    }
}
