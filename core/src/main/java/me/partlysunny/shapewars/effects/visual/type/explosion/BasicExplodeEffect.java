package me.partlysunny.shapewars.effects.visual.type.explosion;

public class BasicExplodeEffect extends ExplodeEffect {
    @Override
    protected float getDuration() {
        return 3;
    }

    @Override
    protected float explosionRadius() {
        return 20;
    }
}
