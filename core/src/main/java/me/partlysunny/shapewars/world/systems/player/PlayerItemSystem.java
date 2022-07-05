package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.player.item.components.ItemComponent;
import me.partlysunny.shapewars.player.item.components.WeaponComponent;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class PlayerItemSystem extends IteratingSystem {

    public PlayerItemSystem() {
        super(Family.all(ItemComponent.class, TransformComponent.class, WeaponComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ItemComponent item = Mappers.itemMapper.get(entity);
        TransformComponent transform = Mappers.transformMapper.get(entity);
        Entity player = item.player();
        TransformComponent playerTransform = Mappers.transformMapper.get(player);
        transform.position.set(playerTransform.position);
        float rotation = playerTransform.rotation;
        float x = (float) (3.5 * Math.cos(rotation));
        float y = (float) (3.5 * Math.sin(rotation));
        transform.position.add(x, y, 0);
        if (Mappers.weaponMapper.has(entity) && Mappers.weaponMapper.get(entity).isSwinging()) {
            return;
        }
        transform.rotation = rotation;
    }
}
