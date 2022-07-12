package me.partlysunny.shapewars.effects.visual.type.explosion;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffect;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ExplodeEffect extends VisualEffect {

    private final Map<Entity, Float> lastBeeped = new HashMap<>();

    protected abstract float explosionRadius();

    @Override
    protected void startEffect(Entity e) {
        lastBeeped.put(e, getDuration());
    }

    protected float soundVolume() {
        return 1;
    }

    @Override
    protected void effectUpdate(Entity e, float delta) {
        if (Mappers.tintMapper.has(e)) {
            TintComponent tint = Mappers.tintMapper.get(e);
            float currentlyOn = effectCountdown.get(e);

            if (currentlyOn > getDuration() / 2f) {
                //Before half, beep every 0.7 secs
                if (lastBeeped.get(e) - currentlyOn > 0.7f) {
                    SoundEffectManager.play("beep",  soundVolume());
                    tint.setTint(1f, 0.4f, 0.4f, 1);
                    lastBeeped.put(e, currentlyOn);
                } else if (lastBeeped.get(e) - currentlyOn >= 0.1f) {
                    tint.setTint(1, 1, 1, 1);
                }
            } else if (currentlyOn > getDuration() * (1 / 6f)) {
                //Before 3/4, beep every 0.4 secs
                if (lastBeeped.get(e) - currentlyOn > 0.4f) {
                    SoundEffectManager.play("beep",  soundVolume());
                    tint.setTint(1f, 0.4f, 0.4f, 1);
                    lastBeeped.put(e, currentlyOn);
                } else if (lastBeeped.get(e) - currentlyOn >= 0.1f) {
                    tint.setTint(1, 1, 1, 1);
                }
            } else {
                //Final bit
                if (lastBeeped.get(e) - currentlyOn > 0.1f) {
                    SoundEffectManager.play("beep",  soundVolume());
                    tint.setTint(1f, 0.4f, 0.4f, 1);
                    lastBeeped.put(e, currentlyOn);
                } else if (lastBeeped.get(e) - currentlyOn >= 0.05f) {
                    tint.setTint(1, 1, 1, 1);
                }
            }
        }
    }

    @Override
    protected void endEffect(Entity e) {
        lastBeeped.remove(e);
        SoundEffectManager.play("explosion", soundVolume());
        TransformComponent entity = Mappers.transformMapper.get(e);
        ParticleEffectManager.startEffect("explosion", (int) TextureRenderingSystem.metersToPixels(entity.position.x), (int) TextureRenderingSystem.metersToPixels(entity.position.y), 100);

        List<Entity> toDamage = InGameScreen.world.getEntitiesAroundPosition(entity.position.x, entity.position.y, explosionRadius(), true);
        BulletRestrictions restrictions = Mappers.bombMapper.get(e).restrictions();
        int damage = Mappers.bombMapper.get(e).damage();
        for (Entity entityToDamage : toDamage) {
            if (Mappers.enemyStateMapper.has(entityToDamage)) {
                if (restrictions != BulletRestrictions.ONLY_PLAYERS) {
                    Util.playDamage(entityToDamage, damage);
                    Util.doKnockback(e, entityToDamage, 120);
                }
            } else if (Mappers.playerStateMapper.has(entityToDamage)) {
                if (restrictions != BulletRestrictions.ONLY_ENTITIES) {
                    InGameScreen.playerInfo.damage(damage);
                    Util.doKnockback(e, entityToDamage, 120);
                }
            }
            if (Mappers.obstacleMapper.has(entityToDamage)) {
                Util.playDamage(entityToDamage, damage);
                Util.doKnockback(e, entityToDamage, 120);
            }
        }

        LateRemover.tagToRemove(e);
    }
}
