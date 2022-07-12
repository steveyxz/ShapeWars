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
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.BulletFactory;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class SniperPlayerBulletController implements BulletController {

    @Override
    public void fire(Entity shooter, float damage) {
        float rotation = shooter.getComponent(TransformComponent.class).rotation;
        float bulletSpeed = 2400;
        float x = MathUtils.cos(rotation) * bulletSpeed;
        float y = MathUtils.sin(rotation) * bulletSpeed;

        TransformComponent t = Mappers.transformMapper.get(shooter);
        t.rotation = rotation;

        Entity basicBullet = BulletFactory.getInstance().generateBullet(InGameScreen.world.gameWorld(), new BulletType("sniperBullet", BulletType.ProjectileHitBox.SNIPER_BULLET), damage, shooter, Controllers.SNIPER, BulletRestrictions.ONLY_ENTITIES);
        Mappers.bodyMapper.get(basicBullet).rigidBody().applyForceToCenter(x, y, true);
        InGameScreen.world.gameWorld().addEntity(basicBullet);
        SoundEffectManager.play("playerShoot", 1);
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
