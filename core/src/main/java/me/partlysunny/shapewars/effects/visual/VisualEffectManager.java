package me.partlysunny.shapewars.effects.visual;

import me.partlysunny.shapewars.effects.visual.type.*;

import java.util.HashMap;
import java.util.Map;

public class VisualEffectManager {

    private static final Map<String, VisualEffect> effects = new HashMap<>();

    public static void registerEffect(String id, VisualEffect effect) {
        effects.put(id, effect);
    }

    public static VisualEffect getEffect(String id) {
        return effects.get(id);
    }

    public static void unregisterEffect(String id) {
        effects.remove(id);
    }

    public static void init() {
        registerEffect("damage", new DamageEffect());
        registerEffect("bulletFade", new BulletFadeEffect());
        registerEffect("playerSwing", new PlayerSwingEffect());
        registerEffect("enemySummon", new EnemySummonEffect());
        registerEffect("explode", new ExplodeEffect());
    }

    public static void update(float delta) {
        for (VisualEffect e : effects.values()) {
            e.update(delta);
        }
    }
}
