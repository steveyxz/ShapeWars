package me.partlysunny.shapewars.world.components.enemy.loot;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

import java.util.function.Consumer;

/**
 * This represents a loot item (say a coin)
 * DON'T GET CONFUSED WITH LootComponent!!!!
 */
public class LootItemComponent implements Component, Pool.Poolable {

    private Entity entity;
    private Consumer<Entity> onPickup;
    private final Vector2 vec = new Vector2();

    public void init(Entity entity, Consumer<Entity> onPickup) {
        this.entity = entity;
        this.onPickup = onPickup;
    }

    public void update(float delta) {
        float movementMultiplier = 360f;
        float maxDist = 20;

        Entity player = InGameScreen.playerInfo.playerEntity();

        TransformComponent playerPos = Mappers.transformMapper.get(player);
        TransformComponent lootItemPos = Mappers.transformMapper.get(entity);

        float xDiff = playerPos.position.x - lootItemPos.position.x;
        float yDiff = playerPos.position.y - lootItemPos.position.y;

        float dist = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        RigidBodyComponent lootItemBody = Mappers.bodyMapper.get(entity);
        Body body = lootItemBody.rigidBody();
        if (dist > maxDist) {
            body.setLinearVelocity(body.getLinearVelocity().x * 0.6f, body.getLinearVelocity().y * 0.6f);
            float lowerBound = 0.01f;
            if (body.getLinearVelocity().x < lowerBound) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
            if (body.getLinearVelocity().y < lowerBound) {
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }
            return;
        }

        vec.set(xDiff, yDiff);
        vec.nor();
        vec.scl(movementMultiplier);

        body.setLinearVelocity(delta * vec.x, delta * vec.y);
    }

    public void pickup() {
        onPickup.accept(entity);
    }

    @Override
    public void reset() {
        entity = null;
        onPickup = null;
    }
}
