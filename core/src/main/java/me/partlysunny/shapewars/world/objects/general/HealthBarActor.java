package me.partlysunny.shapewars.world.objects.general;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;

public class HealthBarActor extends Actor implements Pool.Poolable {

    private Entity parent;
    private final Drawable loadingBarBackground;
    private final Drawable loadingBar;
    private boolean removed = false;

    public HealthBarActor() {
        loadingBar = TextureRegionDrawableCache.get("progressBarKnob");
        loadingBarBackground = TextureRegionDrawableCache.get("progressBar");
    }

    public void init(Entity parent) {
        this.parent = parent;
    }

    public Entity parent() {
        return parent;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (removed) {
            this.remove();
            return;
        }

        HealthComponent health = Mappers.healthMapper.get(parent);
        if (health == null) {
            return;
        }
        float progress = health.health() / health.maxHealth();

        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        loadingBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
    }

    @Override
    public void act(float delta) {

        if (removed) {
            this.remove();
            return;
        }

        TransformComponent parentPos = Mappers.transformMapper.get(parent);
        if (parentPos == null) {
            return;
        }
        this.setPosition(parentPos.position.x - this.getWidth() / 2f, parentPos.position.y - parentPos.scale.y / 2f - this.getHeight() * 2f);
        this.setSize(parentPos.scale.x * 4/3, 1);
    }

    public boolean remove() {
        super.remove();
        removed = true;
        return false;
    }

    @Override
    public void reset() {
        parent = null;
        removed = false;
    }
}
