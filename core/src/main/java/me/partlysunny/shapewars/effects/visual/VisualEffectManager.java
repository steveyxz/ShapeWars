package me.partlysunny.shapewars.effects.visual;

import me.partlysunny.shapewars.effects.visual.type.BulletFadeEffect;
import me.partlysunny.shapewars.effects.visual.type.DamageEffect;
import me.partlysunny.shapewars.effects.visual.type.EnemySummonEffect;
import me.partlysunny.shapewars.effects.visual.type.PlayerSwingEffect;
import me.partlysunny.shapewars.effects.visual.type.explosion.BasicExplodeEffect;
import me.partlysunny.shapewars.effects.visual.type.explosion.FastExplodeEffect;
import me.partlysunny.shapewars.effects.visual.type.explosion.SoftExplodeEffect;
import me.partlysunny.shapewars.effects.visual.type.explosion.SoftFastExplodeEffect;

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
        registerEffect("basicExplode", new BasicExplodeEffect());
        registerEffect("fastExplode", new FastExplodeEffect());
        registerEffect("softFastExplode", new SoftFastExplodeEffect());
        registerEffect("softExplode", new SoftExplodeEffect());
    }

    public static void update(float delta) {
        for (VisualEffect e : effects.values()) {
            e.update(delta);
        }
    }
}
