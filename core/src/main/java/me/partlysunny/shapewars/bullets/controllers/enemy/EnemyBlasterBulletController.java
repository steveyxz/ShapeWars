package me.partlysunny.shapewars.bullets.controllers.enemy;

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
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

import static me.partlysunny.shapewars.util.utilities.Util.handleBasicEnemyBulletCollision;

public class EnemyBlasterBulletController implements BulletController {

    @Override
    public void fire(Entity shooter, float damage) {
        Entity enemyBlasterBullet = BulletFactory.getInstance().generateBullet(InGameScreen.world.gameWorld(), new BulletType("enemyBlasterBullet", BulletType.ProjectileHitBox.BASIC_BULLET), damage, shooter, Controllers.BASIC, BulletRestrictions.ONLY_PLAYERS);
        float rotation = shooter.getComponent(TransformComponent.class).rotation;
        float bulletSpeed = 1800;
        float x = MathUtils.cos(rotation) * bulletSpeed;
        float y = MathUtils.sin(rotation) * bulletSpeed;
        Mappers.bodyMapper.get(enemyBlasterBullet).rigidBody().applyForceToCenter(x, y, true);
        SoundEffectManager.play("enemyBlasterShoot", 1);
        InGameScreen.world.gameWorld().addEntity(enemyBlasterBullet);
    }

    @Override
    public void updateBullet(BulletComponent component, float delta) {
        //Basic bullet doesn't need updating
    }

    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = handleBasicEnemyBulletCollision(contact);
        if (result != null) {
            LateRemover.tagToRemove(result.a());
            InGameScreen.playerInfo.damage((int) Mappers.bulletMapper.get(result.a()).damage());
        }
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
