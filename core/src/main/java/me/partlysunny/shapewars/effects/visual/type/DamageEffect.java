package me.partlysunny.shapewars.effects.visual.type;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import me.partlysunny.shapewars.effects.visual.VisualEffect;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.render.TintComponent;

public class DamageEffect extends VisualEffect {

    @Override
    protected float getDuration() {
        return 0.2f;
    }

    @Override
    protected void startEffect(Entity e) {
        if (Mappers.tintMapper.has(e)) {
            TintComponent tint = Mappers.tintMapper.get(e);
            Vector3 currentTint = tint.tint();
            float damageTintChange = -0.3f;
            tint.setTint(currentTint.x + damageTintChange, currentTint.y + damageTintChange, currentTint.z + damageTintChange, 1);
        }
    }

    @Override
    protected void endEffect(Entity e) {
        if (Mappers.tintMapper.has(e)) {
            TintComponent tint = Mappers.tintMapper.get(e);
            Vector3 currentTint = tint.tint();
            float damageTintChange = 0.3f;
            tint.setTint(currentTint.x + damageTintChange, currentTint.y + damageTintChange, currentTint.z + damageTintChange, 1);

        }
    }
}
