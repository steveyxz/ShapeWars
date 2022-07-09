package me.partlysunny.shapewars.world.components.enemy.loot.entry.coin;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
import me.partlysunny.shapewars.world.components.render.TextureComponent;

public class CoinEntry implements Entry {

    private final int min;
    private final int max;
    private final int maxVisibleCoins;

    public CoinEntry(int min, int max, int maxVisibleCoins) {
        this.min = min;
        this.max = max;
        this.maxVisibleCoins = maxVisibleCoins;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    @Override
    public void execute(Vector2 pos) {
        int moneyToGive = Util.getRandomBetween(min, max);
        int base = moneyToGive / maxVisibleCoins;
        int extras = moneyToGive % maxVisibleCoins;

        float variation = 1;
        for (int i = 0; i < extras; i++) {
            summonCoin(base + 1, pos, variation);
        }
        for (int i = 0; i < maxVisibleCoins - extras; i++) {
            summonCoin(base, pos, variation);
        }
    }

    private void summonCoin(int value, Vector2 around, float variation) {
        PooledEngine engine = InGameScreen.world.gameWorld();
        Entity coin = engine.createEntity();

        CoinComponent money = engine.createComponent(CoinComponent.class);
        money.init(value);
        coin.add(money);

        FixtureDef collision = new FixtureDef();
        collision.isSensor = true;
        CircleShape shape = new CircleShape();
        float radius = 0.8f;
        shape.setRadius(radius);
        collision.shape = shape;

        RigidBodyComponent body = engine.createComponent(RigidBodyComponent.class);
        body.initBody(around.x + (Util.getRandomBetween(0, variation) * (Util.RAND.nextBoolean() ? 1 : -1)), around.y + (Util.getRandomBetween(0, variation) * (Util.RAND.nextBoolean() ? 1 : -1)), 0, collision, BodyDef.BodyType.DynamicBody, radius);
        coin.add(body);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.init(new TextureRegion(TextureManager.getTexture("coin")));
        coin.add(texture);

        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.init(radius * 2, radius * 2);
        coin.add(transform);

        LootItemComponent lootItem = engine.createComponent(LootItemComponent.class);
        lootItem.init(coin, entity -> {
            CoinComponent c = Mappers.coinMapper.get(entity);
            InGameScreen.playerInfo.giveMoney(c.amount());
            SoundEffectManager.play("pickupCoin", 1);
            LateRemover.tagToRemove(entity);
        });
        coin.add(lootItem);

        engine.addEntity(coin);
    }
}
