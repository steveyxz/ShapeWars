package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.item.components.ItemComponent;
import me.partlysunny.shapewars.item.components.WeaponComponent;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class PlayerItemSystem extends IteratingSystem {

    private ComponentMapper<ItemComponent> itemMapper = ComponentMapper.getFor(ItemComponent.class);


    public PlayerItemSystem() {
        super(Family.all(ItemComponent.class, TransformComponent.class, WeaponComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ItemComponent item = itemMapper.get(entity);
        TransformComponent transform = Mappers.transformMapper.get(entity);
        Entity player = item.player();
        TransformComponent playerTransform = Mappers.transformMapper.get(player);
        transform.position.set(playerTransform.position);
        float rotation = playerTransform.rotation;
        float x = (float) (3.5 * Math.cos(rotation));
        float y = (float) (3.5 * Math.sin(rotation));
        transform.position.add(x, y, 0);
        transform.rotation = rotation;
    }
}
