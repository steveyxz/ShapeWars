package me.partlysunny.shapewars.util.factories;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import me.partlysunny.shapewars.TextureManager;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.bullets.BulletController;
import me.partlysunny.shapewars.bullets.BulletType;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.ai.AiDodgeIgnoreComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;


public final class BulletFactory {

    private static final BulletFactory INSTANCE = new BulletFactory();
    private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);

    public static BulletFactory getInstance() {
        return INSTANCE;
    }

    public Entity generateBullet(PooledEngine world, BulletType type, float damage, Entity shooter, BulletController controller) {
        Entity bullet = world.createEntity();
        TransformComponent transformComponent = world.createComponent(TransformComponent.class);
        TextureComponent textureComponent = world.createComponent(TextureComponent.class);
        RigidBodyComponent rigidBodyComponent = world.createComponent(RigidBodyComponent.class);
        BulletComponent bulletComponent = world.createComponent(BulletComponent.class);
        AiDodgeIgnoreComponent aiIgnore = world.createComponent(AiDodgeIgnoreComponent.class);

        bulletComponent.init(shooter, damage, controller);
        textureComponent.init(new TextureRegion(TextureManager.getTexture(type.texture())));
        transformComponent.init(type.width(), type.height());
        TransformComponent shooterPos = transformMapper.get(shooter);
        float rotation = shooterPos.rotation;
        float x = MathUtils.cos(rotation) * 6;
        float y = MathUtils.sin(rotation) * 6;
        rigidBodyComponent.initBody(shooterPos.position.x + x, shooterPos.position.y + y, shooterPos.rotation, type.hitbox().genDef(), BodyDef.BodyType.DynamicBody, type.width() / 2f);

        bullet.add(transformComponent);
        bullet.add(textureComponent);
        bullet.add(rigidBodyComponent);
        bullet.add(bulletComponent);
        bullet.add(aiIgnore);

        return bullet;
    }

}
