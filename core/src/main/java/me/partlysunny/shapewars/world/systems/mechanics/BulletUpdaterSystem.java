package me.partlysunny.shapewars.world.systems.mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.util.constants.Mappers;

public class BulletUpdaterSystem extends IteratingSystem {


    public BulletUpdaterSystem() {
        super(Family.all(BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BulletComponent bullet = Mappers.bulletMapper.get(entity);
        bullet.update(deltaTime);
    }
}
