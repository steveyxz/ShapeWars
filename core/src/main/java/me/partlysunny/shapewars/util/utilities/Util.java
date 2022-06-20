package me.partlysunny.shapewars.util.utilities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();
    private static final ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    private static final ComponentMapper<PlayerControlComponent> playerMapper = ComponentMapper.getFor(PlayerControlComponent.class);
    private static final ComponentMapper<BulletDeleterComponent> deletionMapper = ComponentMapper.getFor(BulletDeleterComponent.class);

    public static int getRandomBetween(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be higher than b");
        }
        if (a == b) {
            return a;
        }
        if (a < 0 && b < 0) {
            return -getRandomBetween(-b, -a);
        }
        if (a < 0) {
            return getRandomBetween(0, -a + b) + a;
        }
        return RAND.nextInt(b - a) + a;
    }

    /**
     * Handle basic collision, will not damage the entities, only validate
     *
     * @return A pair of the bullet and the entity or null if invalid
     */
    @Nullable
    public static Pair<Entity, Entity> handleBasicCollision(Contact contact) {
        GameWorld world = InGameScreen.world;
        Entity a = world.getEntityWithRigidBody(contact.getFixtureA().getBody());
        Entity b = world.getEntityWithRigidBody(contact.getFixtureB().getBody());
        if (a == null || b == null) {
            return null;
        }
        Entity bullet = null;
        Entity other = null;
        if (bulletMapper.has(a)) {
            bullet = a;
            other = b;
        }
        if (bulletMapper.has(b)) {
            if (bullet != null) {
                LateRemover.tagToRemove(a);
                LateRemover.tagToRemove(b);
            }
            bullet = b;
            other = a;
        }
        if (other == null) {
            return null;
        } else if (!healthMapper.has(other) || playerMapper.has(other)) {
            if (deletionMapper.has(other)) {
                LateRemover.tagToRemove(bullet);
            }
            return null;
        }
        return new Pair<>(bullet, other);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.set(MathUtils.cos(angle), MathUtils.sin(angle));
        return outVector;
    }

    public static float vectorToAngle(Vector2 angle) {
        return MathUtils.atan2(angle.y, angle.x);
    }
}
