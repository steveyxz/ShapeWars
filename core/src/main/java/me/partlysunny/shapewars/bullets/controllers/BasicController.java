package me.partlysunny.shapewars.bullets.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.LateRemover;
import me.partlysunny.shapewars.util.Pair;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.bullets.BulletController;
import me.partlysunny.shapewars.bullets.BulletFactory;
import me.partlysunny.shapewars.bullets.BulletType;

import javax.annotation.Nullable;

public class BasicController implements BulletController {

    private static final ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    private static final ComponentMapper<PlayerControlComponent> playerMapper = ComponentMapper.getFor(PlayerControlComponent.class);
    private static final ComponentMapper<BulletDeleterComponent> deletionMapper = ComponentMapper.getFor(BulletDeleterComponent.class);

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

    @Override
    public void fire(Entity player, float damage) {
        Entity basicBullet = BulletFactory.getInstance().generateBullet(InGameScreen.world.gameWorld(), new BulletType("basicBullet", BulletType.ProjectileHitBox.BASIC_BULLET), damage, player, Controllers.BASIC);
        float rotation = player.getComponent(TransformComponent.class).rotation;
        float bulletSpeed = 500;
        float x = MathUtils.cos(rotation) * bulletSpeed;
        float y = MathUtils.sin(rotation) * bulletSpeed;
        basicBullet.getComponent(RigidBodyComponent.class).rigidBody().applyForceToCenter(x, y, true);
        InGameScreen.world.gameWorld().addEntity(basicBullet);
    }

    @Override
    public void updateBullet(BulletComponent component, float delta) {
    }

    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = handleBasicCollision(contact);
        if (result != null) {
            HealthComponent health = healthMapper.get(result.b());
            health.addHealth(-bulletMapper.get(result.a()).damage());
            LateRemover.tagToRemove(result.a());
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
