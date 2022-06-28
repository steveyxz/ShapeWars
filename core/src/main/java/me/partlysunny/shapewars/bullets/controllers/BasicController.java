package me.partlysunny.shapewars.bullets.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.bullets.BulletController;
import me.partlysunny.shapewars.bullets.BulletType;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.factories.BulletFactory;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;

import static me.partlysunny.shapewars.util.utilities.Util.handleBasicCollision;

public class BasicController implements BulletController {

    private static final ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    private static final ComponentMapper<PlayerControlComponent> playerMapper = ComponentMapper.getFor(PlayerControlComponent.class);
    private static final ComponentMapper<BulletDeleterComponent> deletionMapper = ComponentMapper.getFor(BulletDeleterComponent.class);
    private static final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);


    @Override
    public void fire(Entity player, float damage) {
        Entity basicBullet = BulletFactory.getInstance().generateBullet(InGameScreen.world.gameWorld(), new BulletType("basicBullet", BulletType.ProjectileHitBox.BASIC_BULLET), damage, player, Controllers.BASIC);
        float rotation = player.getComponent(TransformComponent.class).rotation;
        float bulletSpeed = 500;
        float x = MathUtils.cos(rotation) * bulletSpeed;
        float y = MathUtils.sin(rotation) * bulletSpeed;
        bodyMapper.get(basicBullet).rigidBody().applyForceToCenter(x, y, true);
        SoundEffectManager.play("playerShoot", 1);
        InGameScreen.world.gameWorld().addEntity(basicBullet);
    }

    @Override
    public void updateBullet(BulletComponent component, float delta) {
    }

    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = handleBasicCollision(contact);
        if (result != null) {
            Util.playDamage(result);
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
