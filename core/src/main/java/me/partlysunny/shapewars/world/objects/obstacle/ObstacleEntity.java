package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.collision.*;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;
import me.partlysunny.shapewars.world.objects.GameObject;
import me.partlysunny.shapewars.world.objects.general.HealthBarFactory;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import java.util.function.Consumer;

public abstract class ObstacleEntity extends SteerableAdapter<Vector2> implements GameObject {

    public static Entity addObstacle(PooledEngine w, ObstacleEntity oe, int x, int y, int width, int height, int angle) {
        Entity e = w.createEntity();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);
        FixtureDef def = new FixtureDef();
        def.friction = 0.2f;
        def.shape = shape;
        RigidBodyComponent rigidBody = w.createComponent(RigidBodyComponent.class);
        rigidBody.initBody(x, y, angle, def, BodyDef.BodyType.DynamicBody, width / 2f);
        e.add(rigidBody);
        GroundFrictionComponent groundFrictionComponent = w.createComponent(GroundFrictionComponent.class);
        groundFrictionComponent.setFriction(2);
        e.add(groundFrictionComponent);
        TextureComponent texture = w.createComponent(TextureComponent.class);
        Texture textureReal = TextureManager.getTexture(oe.getTexture());
        texture.init(new TextureRegion(textureReal));
        TransformComponent scale = w.createComponent(TransformComponent.class);
        scale.init(width, height);
        e.add(scale);
        e.add(texture);
        e.add(w.createComponent(BulletDeleterComponent.class));
        e.add(w.createComponent(TintComponent.class));
        e.add(w.createComponent(ObstacleComponent.class));
        HealthComponent health = w.createComponent(HealthComponent.class);
        health.init(oe.getHealth());
        e.add(health);
        Entity healthBar = HealthBarFactory.createEntity(w, e);
        DeletionListenerComponent deleteListener = w.createComponent(DeletionListenerComponent.class);
        deleteListener.init(entity -> {
            oe.onDestroy().accept(entity);
            ParticleEffectManager.startEffect("obstacleBreak", (int) TextureRenderingSystem.metersToPixels(scale.position.x), (int) TextureRenderingSystem.metersToPixels(scale.position.y), 200);
            SoundEffectManager.play("obstacleBreak", 1);
            LateRemover.tagToRemove(healthBar);
        });
        e.add(deleteListener);

        w.addEntity(e);
        w.addEntity(healthBar);
        return e;
    }

    protected abstract String getTexture();

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract int getAngle();

    protected abstract int getHealth();

    protected abstract Consumer<Entity> onDestroy();

    @Override
    public Entity createEntity(PooledEngine w, float originalX, float originalY) {
        return ObstacleEntity.addObstacle(w, this, (int) originalX, (int) originalY, getWidth(), getHeight(), getAngle());
    }

    public Entity spawnAt(float x, float y) {
        PooledEngine pooledEngine = InGameScreen.world.gameWorld();
        return createEntity(pooledEngine, x, y);
    }


}
