package me.partlysunny.shapewars.util.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.bullets.BulletController;
import me.partlysunny.shapewars.bullets.BulletType;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.ai.AiDodgeIgnoreComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;


public final class BulletFactory {

    private static final BulletFactory INSTANCE = new BulletFactory();


    public static BulletFactory getInstance() {
        return INSTANCE;
    }

    public Entity generateBullet(PooledEngine world, BulletType type, float damage, Entity shooter, BulletController controller, BulletRestrictions restrictions) {
        Entity bullet = world.createEntity();
        TransformComponent transformComponent = world.createComponent(TransformComponent.class);
        TextureComponent textureComponent = world.createComponent(TextureComponent.class);
        RigidBodyComponent rigidBodyComponent = world.createComponent(RigidBodyComponent.class);
        BulletComponent bulletComponent = world.createComponent(BulletComponent.class);
        TintComponent tintComponent = world.createComponent(TintComponent.class);
        AiDodgeIgnoreComponent aiIgnore = world.createComponent(AiDodgeIgnoreComponent.class);

        bulletComponent.init(shooter, bullet, damage, controller, restrictions);
        textureComponent.init(new TextureRegion(TextureManager.getTexture(type.texture())));
        transformComponent.init(type.width(), type.height());
        TransformComponent shooterPos = Mappers.transformMapper.get(shooter);
        float rotation = shooterPos.rotation;
        float x = MathUtils.cos(rotation) * 5;
        float y = MathUtils.sin(rotation) * 5;
        rigidBodyComponent.initBody(shooterPos.position.x + x, shooterPos.position.y + y, shooterPos.rotation, type.hitbox().genDef(), BodyDef.BodyType.DynamicBody, type.width() / 2f);
        transformComponent.position.set(shooterPos.position.x + x, shooterPos.position.y + y, 0);

        bullet.add(transformComponent);
        bullet.add(textureComponent);
        bullet.add(rigidBodyComponent);
        bullet.add(bulletComponent);
        bullet.add(aiIgnore);
        bullet.add(tintComponent);

        return bullet;
    }

}
