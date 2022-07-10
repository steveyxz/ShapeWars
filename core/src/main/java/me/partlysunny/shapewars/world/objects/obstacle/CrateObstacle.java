package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinEntry;

import java.util.function.Consumer;

public class CrateObstacle extends ObstacleEntity {

    private final Vector2 vec = new Vector2();

    @Override
    protected String getTexture() {
        return "crate";
    }

    @Override
    protected int getWidth() {
        return 7;
    }

    @Override
    protected int getHeight() {
        return 7;
    }

    @Override
    protected int getAngle() {
        return 0;
    }

    @Override
    protected int getHealth() {
        return 60;
    }

    @Override
    protected Consumer<Entity> onDestroy() {
        return e -> {
            int coinCount = Util.getRandomBetween(3, 5);
            for (int i = 0; i < coinCount; i++) {
                int coinValue = Util.getRandomBetween(1, 3);
                Vector3 position = Mappers.transformMapper.get(e).position;
                vec.set(position.x, position.y);
                CoinEntry.summonCoin(coinValue, vec, 2);
            }
        };
    }
}
