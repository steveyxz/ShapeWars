package me.partlysunny.shapewars.effects.visual.type;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.effects.visual.VisualEffect;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.item.WeaponComponent;

public class PlayerSwingEffect extends VisualEffect {
    @Override
    protected float getDuration() {
        return 0.2f;
    }

    @Override
    protected void startEffect(Entity e) {
        //START THIS ON THE ITEM ENTITY
        WeaponComponent weapon = Mappers.weaponMapper.get(e);
        weapon.setSwinging(true);
    }

    @Override
    protected void effectUpdate(Entity e, float delta) {
        TransformComponent transform = Mappers.transformMapper.get(e);
        float currentlyOn = effectCountdown.get(e);
        if (currentlyOn > getDuration() / 2f) {
            transform.rotation -= (delta * 30);
        } else {
            transform.rotation += (delta * 30);
        }
    }

    @Override
    protected void endEffect(Entity e) {
        WeaponComponent weapon = Mappers.weaponMapper.get(e);
        weapon.setSwinging(false);
    }
}
