package me.partlysunny.shapewars.effects.visual;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class VisualEffect {

    private final Map<Entity, Float> effectCountdown = new HashMap<>();

    protected abstract float getDuration();

    protected abstract void startEffect(Entity e);

    protected abstract void endEffect(Entity e);

    public void playEffect(Entity e) {
        if (!effectCountdown.containsKey(e)) {
            startEffect(e);
        }
        effectCountdown.put(e, getDuration());
    }

    public void update(float delta) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : effectCountdown.keySet()) {
            effectCountdown.put(e, effectCountdown.get(e) - delta);
            if (effectCountdown.get(e) < 0) {
                endEffect(e);
                r.add(e);
            }
        }
        r.forEach(effectCountdown::remove);
    }

}
