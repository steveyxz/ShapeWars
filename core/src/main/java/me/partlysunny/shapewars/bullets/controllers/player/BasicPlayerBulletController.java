package me.partlysunny.shapewars.bullets.controllers.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.bullets.BulletController;
import me.partlysunny.shapewars.bullets.BulletType;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.BulletFactory;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

import static me.partlysunny.shapewars.util.utilities.Util.handleBasicPlayerBulletCollision;

public class BasicPlayerBulletController implements BulletController {

    @Override
    public void fire(Entity shooter, float damage) {
        Entity basicBullet = BulletFactory.getInstance().generateBullet(InGameScreen.world.gameWorld(), new BulletType("basicBullet", BulletType.ProjectileHitBox.BASIC_BULLET), damage, shooter, Controllers.BASIC, BulletRestrictions.ONLY_ENTITIES);
        float rotation = shooter.getComponent(TransformComponent.class).rotation;
        float bulletSpeed = 500;
        float x = MathUtils.cos(rotation) * bulletSpeed;
        float y = MathUtils.sin(rotation) * bulletSpeed;
        Mappers.bodyMapper.get(basicBullet).rigidBody().applyForceToCenter(x, y, true);
        SoundEffectManager.play("playerShoot", 1);
        InGameScreen.world.gameWorld().addEntity(basicBullet);
    }

    @Override
    public void updateBullet(BulletComponent component, float delta) {
    }

    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = handleBasicPlayerBulletCollision(contact);
        if (result != null) {
            Util.playDamage(result.b(), (int) Mappers.bulletMapper.get(result.a()).damage());
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
