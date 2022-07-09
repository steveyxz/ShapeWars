package me.partlysunny.shapewars.effects.visual.type;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.effects.visual.VisualEffect;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.render.TintComponent;

public class EnemySummonEffect extends VisualEffect {
    @Override
    protected float getDuration() {
        return 2;
    }

    @Override
    protected void startEffect(Entity e) {
    }

    @Override
    protected void effectUpdate(Entity e, float delta) {
        if (Mappers.tintMapper.has(e)) {
            TintComponent tint = Mappers.tintMapper.get(e);
            float rChange = Util.getRandomBetween(-0.01f, 0.01f);
            float bChange = Util.getRandomBetween(-0.01f, 0.01f);
            float gChange = Util.getRandomBetween(-0.01f, 0.01f);
            tint.setTint(tint.tint().x + rChange, tint.tint().y + bChange, tint.tint().z + gChange, tint.alpha());
        }
    }

    @Override
    protected void endEffect(Entity e) {
        if (Mappers.tintMapper.has(e)) {
            TintComponent tint = Mappers.tintMapper.get(e);
            tint.setTint(1, 1, 1, 1);
        }
    }
}
