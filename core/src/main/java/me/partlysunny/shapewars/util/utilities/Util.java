package me.partlysunny.shapewars.util.utilities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.level.Level;
import me.partlysunny.shapewars.util.classes.PositionSet;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.WallComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.world.components.render.DeathEffectComponent;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();
    private static final ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    private static final ComponentMapper<PlayerControlComponent> playerMapper = ComponentMapper.getFor(PlayerControlComponent.class);
    private static final ComponentMapper<BulletDeleterComponent> deletionMapper = ComponentMapper.getFor(BulletDeleterComponent.class);
    private static final ComponentMapper<DeathEffectComponent> deathEffectMapper = ComponentMapper.getFor(DeathEffectComponent.class);

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
        if (a.isScheduledForRemoval() || a.isRemoving() || b.isScheduledForRemoval() || b.isRemoving()) {
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

    public static void playDamage(Pair<Entity, Entity> result) {
        Entity bullet = result.a();
        Entity victim = result.b();
        HealthComponent health = healthMapper.get(victim);
        health.addHealth(-bulletMapper.get(bullet).damage());
        VisualEffectManager.getEffect("damage").playEffect(victim);
        LateRemover.tagToRemove(bullet);
        if (deathEffectMapper.has(victim) && health.health() <= 0) {
            DeathEffectComponent deathEffect = deathEffectMapper.get(victim);
            deathEffect.die();
        }
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.set(MathUtils.cos(angle), MathUtils.sin(angle));
        return outVector;
    }

    public static void getPositionInLevelAwayFromCenter(PositionSet toLoad, float distance, Level level) {
        int x = Util.getRandomBetween(level.levelWidth() / -2 + GameInfo.BOUNDARY_WIDTH, level.levelWidth() / 2 - GameInfo.BOUNDARY_WIDTH);
        int y = Util.getRandomBetween(level.levelHeight() / -2 + GameInfo.BOUNDARY_WIDTH, level.levelHeight() / 2 - GameInfo.BOUNDARY_WIDTH);
        if (Math.abs(x) < distance || Math.abs(y) < distance) {
            getPositionInLevelAwayFromCenter(toLoad, distance, level);
        }
        toLoad.loadPosition(x, y);
    }

    public static float vectorToAngle(Vector2 angle) {
        return MathUtils.atan2(angle.y, angle.x);
    }
}
