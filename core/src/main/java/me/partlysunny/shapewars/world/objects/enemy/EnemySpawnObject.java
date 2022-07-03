package me.partlysunny.shapewars.world.objects.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemySpawnIndicatorComponent;
import me.partlysunny.shapewars.world.objects.GameObject;

public class EnemySpawnObject implements GameObject {

    @Override
    public Entity createEntity(PooledEngine w, float originalX, float originalY) {
        Entity indicator = w.createEntity();

        EnemySpawnIndicatorComponent enemySpawnIndicatorComponent = w.createComponent(EnemySpawnIndicatorComponent.class);
        enemySpawnIndicatorComponent.init(indicator);
        indicator.add(enemySpawnIndicatorComponent);

        TransformComponent transform = w.createComponent(TransformComponent.class);
        float sideLength = 5;
        transform.init(sideLength, sideLength);
        transform.position.set(originalX, originalY, 0);
        indicator.add(transform);

        TextureComponent texture = w.createComponent(TextureComponent.class);
        texture.init(new TextureRegion(TextureManager.getTexture("spawnIndicator")), 2);
        indicator.add(texture);

        w.addEntity(indicator);

        return indicator;
    }

}
