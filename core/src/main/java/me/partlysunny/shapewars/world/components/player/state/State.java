package me.partlysunny.shapewars.world.components.player.state;

public enum State {

    MOVING(1),
    PASSIVE(0),
    ATTACKING(2);
    private final int value;

    State(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
