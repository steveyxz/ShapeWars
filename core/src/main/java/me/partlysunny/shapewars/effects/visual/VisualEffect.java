package me.partlysunny.shapewars.effects.visual;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import me.partlysunny.shapewars.screens.InGameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class VisualEffect {

    protected final Map<Entity, Float> effectCountdown = new HashMap<>();

    protected abstract float getDuration();

    protected abstract void startEffect(Entity e);

    protected abstract void effectUpdate(Entity e, float delta);

    protected abstract void endEffect(Entity e);

    public void playEffect(Entity e) {
        if (!effectCountdown.containsKey(e)) {
            InGameScreen.world.gameWorld().addEntityListener(new EntityListener() {
                @Override
                public void entityAdded(Entity entity) {
                    //No need to listener for adding
                }

                @Override
                public void entityRemoved(Entity entity) {
                    if (entity.equals(e)) {
                        effectCountdown.remove(e);
                    }
                }
            });
            startEffect(e);
        }
        effectCountdown.put(e, getDuration());
    }

    public void update(float delta) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : effectCountdown.keySet()) {
            effectUpdate(e, delta);
            effectCountdown.put(e, effectCountdown.get(e) - delta);
            if (effectCountdown.get(e) < 0) {
                endEffect(e);
                r.add(e);
            }
        }
        r.forEach(effectCountdown::remove);
    }

}
