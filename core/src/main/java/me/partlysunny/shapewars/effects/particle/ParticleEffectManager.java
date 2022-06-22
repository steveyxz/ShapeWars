package me.partlysunny.shapewars.effects.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.partlysunny.shapewars.screens.InGameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.pixelsToMeters;

public class ParticleEffectManager {

    private static final Map<String, ParticleEffectPool> effects = new HashMap<>();
    private static final Map<String, List<PooledEffect>> existingEffects = new HashMap<>();

    public static void registerEffect(String id, ParticleEffectPool effect) {
        effects.put(id, effect);
        existingEffects.put(id, new ArrayList<>());
    }

    public static ParticleEffectPool getEffect(String id) {
        return effects.get(id);
    }

    public static void unregisterEffect(String id) {
        effects.remove(id);
    }

    public static void init() {
        registerEffect("enemyMeleeAttack", load("enemyMeleeAttack.p", ""));
    }

    public static void startEffect(String effect, int x, int y, int duration) {
        ParticleEffectPool pool = effects.get(effect);
        if (pool == null) {
            return;
        }
        PooledEffect newEffect = pool.obtain();
        newEffect.setPosition(pixelsToMeters(x), pixelsToMeters(y));
        newEffect.setDuration(duration);
        existingEffects.get(effect).add(newEffect);
    }

    public static void render(SpriteBatch batch, float delta) {
        batch.begin();
        batch.setProjectionMatrix(InGameScreen.camera.combined);
        batch.disableBlending();
        for (String s : existingEffects.keySet()) {
            List<PooledEffect> r = new ArrayList<>();
            for (PooledEffect pe : existingEffects.get(s)) {
                pe.draw(batch, delta);
                if (pe.isComplete()) {
                    pe.free();
                    r.add(pe);
                }
            }
            r.forEach(e -> existingEffects.get(s).remove(e));
        }
        batch.enableBlending();
        batch.end();
    }

    private static ParticleEffectPool load(String effectPath, String texturePath) {
        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal(effectPath), Gdx.files.internal(texturePath));
        ParticleEffectPool pool = new ParticleEffectPool(effect, 100, 10000);
        return pool;
    }

}
