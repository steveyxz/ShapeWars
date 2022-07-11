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
import me.partlysunny.shapewars.world.components.enemy.EnemyState;
import me.partlysunny.shapewars.world.components.enemy.EnemyStateComponent;

import static me.partlysunny.shapewars.util.utilities.Util.handleBasicPlayerBulletCollision;

public class ShotgunPlayerBulletController implements BulletController {
    private static final float angleDiff = 10 * MathUtils.degreesToRadians;

    @Override
    public void fire(Entity shooter, float damage) {
        float rotation = shooter.getComponent(TransformComponent.class).rotation;
        fire(rotation, damage, shooter);
        fire(rotation - angleDiff, damage, shooter);
        fire(rotation - 2 * angleDiff, damage, shooter);
        fire(rotation + angleDiff, damage, shooter);
        fire(rotation + 2 * angleDiff, damage, shooter);
        SoundEffectManager.play("shotgunShoot", 1);
    }

    private void fire(float angle, float damage, Entity shooter) {
        float bulletSpeed = 1200;
        float x = MathUtils.cos(angle) * bulletSpeed;
        float y = MathUtils.sin(angle) * bulletSpeed;

        TransformComponent t = Mappers.transformMapper.get(shooter);
        t.rotation = angle;

        Entity basicBullet = BulletFactory.getInstance().generateBullet(InGameScreen.world.gameWorld(), new BulletType("shotgunBullet", BulletType.ProjectileHitBox.SHOTGUN_BULLET), damage, shooter, Controllers.SHOTGUN, BulletRestrictions.ONLY_ENTITIES);
        Mappers.bodyMapper.get(basicBullet).rigidBody().applyForceToCenter(x, y, true);
        InGameScreen.world.gameWorld().addEntity(basicBullet);
    }

    @Override
    public void updateBullet(BulletComponent component, float delta) {
        //No updating needed
    }

    @Override
    public void beginContact(Contact contact) {
        //This is actually managed by the basic controller already so no need :D
    }

    @Override
    public void endContact(Contact contact) {
        //Nothing special
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Unnecessary
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //Unnecessary
    }
}
