package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.util.factories.BombFactory;

import java.util.function.Consumer;

public class TntObstacle extends ObstacleEntity {
    @Override
    protected String getTexture() {
        return "tnt";
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
        return 10;
    }

    @Override
    protected Consumer<Entity> onDestroy() {
        return e -> {
            BombFactory.getInstance().generateBomb(e, 3.5f, 0, BulletRestrictions.BOTH, 10, 0, "beep", "tnt", "basicExplode");
        };
    }
}
