package me.partlysunny.shapewars.world.components.enemy.loot.entry.item;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.enemy.loot.LootItemComponent;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.Entry;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinComponent;
import me.partlysunny.shapewars.world.components.render.TextureComponent;

public class UtilityItemEntry implements Entry {

    private final int min;
    private final int max;
    private final String type;

    public UtilityItemEntry(int min, int max, String type) {
        this.min = min;
        this.max = max;
        this.type = type;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    @Override
    public void execute(Vector2 pos) {
        int amount = Util.getRandomBetween(min, max);
        for (int i = 0; i < amount; i++) {
            summonItem(pos, 1);
        }
    }

    private void summonItem(Vector2 around, float variation) {
        PooledEngine engine = InGameScreen.world.gameWorld();
        Entity item = engine.createEntity();

        UtilityComponent utilComponent = engine.createComponent(UtilityComponent.class);
        utilComponent.init(type);
        item.add(utilComponent);

        FixtureDef collision = new FixtureDef();
        collision.isSensor = true;
        PolygonShape shape = new PolygonShape();
        float radius = 3;
        shape.setAsBox(radius, radius);
        shape.setRadius(radius);
        collision.shape = shape;

        RigidBodyComponent body = engine.createComponent(RigidBodyComponent.class);
        body.initBody(around.x + (variation * (Util.RAND.nextBoolean() ? 1 : -1)), around.y + (variation * (Util.RAND.nextBoolean() ? 1 : -1)), 0, collision, BodyDef.BodyType.DynamicBody, radius);
        item.add(body);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.init(new TextureRegion(TextureManager.getTexture(type)));
        item.add(texture);

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.init(radius * 2, radius * 2);
        item.add(transform);

        LootItemComponent lootItem = engine.createComponent(LootItemComponent.class);
        lootItem.init(item, entity -> {
            UtilityComponent c = Mappers.utilityMapper.get(entity);
            InGameScreen.playerInfo.equipment().addUtilItems(c.type(), 1);
            SoundEffectManager.play("pickupItem", 1);
            LateRemover.tagToRemove(entity);
        });
        item.add(lootItem);

        engine.addEntity(item);
    }
}
