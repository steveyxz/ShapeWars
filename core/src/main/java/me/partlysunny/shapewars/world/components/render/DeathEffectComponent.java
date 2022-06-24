package me.partlysunny.shapewars.world.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

public class DeathEffectComponent implements Component, Pool.Poolable {

    private String effect = "";
    private Entity parent = null;
    private int duration = 0;

    public void init(String effect, Entity parent, int duration) {
        this.effect = effect;
        this.parent = parent;
        this.duration = duration;
    }

    public String effect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Entity parent() {
        return parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public int duration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void die() {
        TransformComponent transform = parent.getComponent(TransformComponent.class);
        ParticleEffectManager.startEffect(effect, (int) TextureRenderingSystem.metersToPixels(transform.position.x), (int) TextureRenderingSystem.metersToPixels(transform.position.y), duration);
    }

    @Override
    public void reset() {
        effect = "";
        parent = null;
        duration = 0;
    }
}
