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

    @Override
    protected void effectUpdate(Entity e, float delta) {
        if (Mappers.tintMapper.has(e)) {
            TintComponent tint = Mappers.tintMapper.get(e);
            float currentlyOn = effectCountdown.get(e);

            if (currentlyOn > getDuration() / 2f) {
                //Before half, beep every 0.7 secs
                if (lastBeeped.get(e) - currentlyOn > 0.7f) {
                    SoundEffectManager.play("beep", 1);
                    tint.setTint(1f, 0.4f, 0.4f, 1);
                    lastBeeped.put(e, currentlyOn);
                } else if (lastBeeped.get(e) - currentlyOn >= 0.1f) {
                    tint.setTint(1, 1, 1, 1);
                }
            } else if (currentlyOn > getDuration() * (1 / 6f)) {
                //Before 3/4, beep every 0.4 secs
                if (lastBeeped.get(e) - currentlyOn > 0.4f) {
                    SoundEffectManager.play("beep", 1);
                    tint.setTint(1f, 0.4f, 0.4f, 1);
                    lastBeeped.put(e, currentlyOn);
                } else if (lastBeeped.get(e) - currentlyOn >= 0.1f) {
                    tint.setTint(1, 1, 1, 1);
                }
            } else {
                //Final bit
                if (lastBeeped.get(e) - currentlyOn > 0.1f) {
                    SoundEffectManager.play("beep", 1);
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
        SoundEffectManager.play("explosion", 1);
        TransformComponent entity = Mappers.transformMapper.get(e);
        ParticleEffectManager.startEffect("explosion", (int) TextureRenderingSystem.metersToPixels(entity.position.x), (int) TextureRenderingSystem.metersToPixels(entity.position.y), 100);

        List<Entity> toDamage = InGameScreen.world.getEntitiesAroundPosition(entity.position.x, entity.position.y, explosionRadius(), true);

        for (Entity entityToDamage : toDamage) {
            if (Mappers.healthMapper.has(entityToDamage)) {
                BulletRestrictions restrictions = Mappers.bombMapper.get(e).restrictions();
                switch (restrictions) {
                    case ONLY_PLAYERS: {
                        if (!Mappers.enemyStateMapper.has(entityToDamage)) {
                            Util.playDamage(entityToDamage, Mappers.bombMapper.get(e).damage());
                        }
                        break;
                    }
                    case ONLY_ENTITIES: {
                        if (!Mappers.playerStateMapper.has(entityToDamage)) {
                            Util.playDamage(entityToDamage, Mappers.bombMapper.get(e).damage());
                        }
                        break;
                    }
                    case BOTH: {
                        Util.playDamage(entityToDamage, Mappers.bombMapper.get(e).damage());
                        break;
                    }
                }
            }
        }

        LateRemover.tagToRemove(e);
    }
}