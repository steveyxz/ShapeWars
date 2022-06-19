package me.partlysunny.shapewars.world.systems.mechanics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.bullets.BulletComponent;

public class BulletUpdaterSystem extends IteratingSystem {

    private ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);

    public BulletUpdaterSystem() {
        super(Family.all(BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BulletComponent bullet = bulletMapper.get(entity);
        bullet.update(deltaTime);
    }
}
