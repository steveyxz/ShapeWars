package me.partlysunny.shapewars.util.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.ai.AiDodgeIgnoreComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.BombComponent;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;

public final class BombFactory {

    public static final BombFactory INSTANCE = new BombFactory();

    public static BombFactory getInstance() {
        return INSTANCE;
    }

    public Entity generateBomb(Entity attacker, float bombRadius, float launchDistanceFromAttacker, BulletRestrictions restrictions, int damage, float speed, String soundEffect, String texture, String explodeEffect) {
        PooledEngine world = InGameScreen.world.gameWorld();
        Entity basicBomb = world.createEntity();
        TransformComponent transformComponent = world.createComponent(TransformComponent.class);
        TextureComponent textureComponent = world.createComponent(TextureComponent.class);
        RigidBodyComponent rigidBodyComponent = world.createComponent(RigidBodyComponent.class);
        TintComponent tintComponent = world.createComponent(TintComponent.class);
        AiDodgeIgnoreComponent aiIgnore = world.createComponent(AiDodgeIgnoreComponent.class);
        BombComponent bombComponent = world.createComponent(BombComponent.class);

        bombComponent.init(damage, restrictions);
        textureComponent.init(new TextureRegion(TextureManager.getTexture(texture)));
        transformComponent.init(bombRadius * 2, bombRadius * 2);
        TransformComponent shooterPos = Mappers.transformMapper.get(attacker);
        float rotation = shooterPos.rotation;
        float x = MathUtils.cos(rotation) * launchDistanceFromAttacker;
        float y = MathUtils.sin(rotation) * launchDistanceFromAttacker;

        FixtureDef def = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(bombRadius);
        def.shape = shape;

        rigidBodyComponent.initBody(shooterPos.position.x + x, shooterPos.position.y + y, shooterPos.rotation, def, BodyDef.BodyType.DynamicBody, 2);
        transformComponent.position.set(shooterPos.position.x + x, shooterPos.position.y + y, 0);

        basicBomb.add(transformComponent);
        basicBomb.add(textureComponent);
        basicBomb.add(rigidBodyComponent);
        basicBomb.add(aiIgnore);
        basicBomb.add(tintComponent);
        basicBomb.add(bombComponent);

        x *= (speed / 5);
        y *= (speed / 5);

        Mappers.bodyMapper.get(basicBomb).rigidBody().applyForceToCenter(x, y, true);
        SoundEffectManager.play(soundEffect, 1);
        InGameScreen.world.gameWorld().addEntity(basicBomb);
        VisualEffectManager.getEffect(explodeEffect).playEffect(basicBomb);

        return basicBomb;
    }

}
