package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.factories.Box2DFactory;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.ai.EnemyAttackComponent;
import me.partlysunny.shapewars.world.components.ai.SteeringComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;
import me.partlysunny.shapewars.world.components.player.PlayerTargetComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;
import me.partlysunny.shapewars.world.objects.GameObject;
import me.partlysunny.shapewars.world.objects.enemy.EnemyRadiusProximity;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

public abstract class Enemy implements GameObject {


    protected abstract float getHealth();

    protected abstract String getTexture();

    protected abstract float getFrictionalResistance();

    protected abstract float getWidth();

    protected abstract Shape getShape(float width);

    protected abstract float getMaxSpeed();

    protected abstract EnemyAttackSelector selector();

    @Override
    public Entity createEntity(PooledEngine w, float originalX, float originalY) {
        Entity enemy = w.createEntity();
        Shape shape = getShape(getWidth());
        FixtureDef def = Box2DFactory.getInstance(InGameScreen.world.physicsWorld()).generateFixture(Box2DFactory.Material.LIGHT, shape);
        //Set components
        RigidBodyComponent rigidBody = w.createComponent(RigidBodyComponent.class);
        rigidBody.initBody(originalX, originalY, 0, def, BodyDef.BodyType.DynamicBody, getWidth() / 2f, true);
        enemy.add(rigidBody);

        TransformComponent scale = w.createComponent(TransformComponent.class);
        scale.init(getWidth(), getWidth());
        enemy.add(scale);

        TextureComponent texture = w.createComponent(TextureComponent.class);
        texture.init(new TextureRegion(TextureManager.getTexture(getTexture())));
        enemy.add(texture);

        enemy.add(w.createComponent(BulletDeleterComponent.class));

        HealthComponent health = w.createComponent(HealthComponent.class);
        health.init(getHealth());
        enemy.add(health);

        GroundFrictionComponent groundFriction = w.createComponent(GroundFrictionComponent.class);
        groundFriction.setFriction(getFrictionalResistance());
        enemy.add(groundFriction);

        EnemyAttackComponent attack = w.createComponent(EnemyAttackComponent.class);
        attack.init(selector(), enemy);
        enemy.add(attack);

        enemy.add(w.createComponent(TintComponent.class));

        SteeringComponent steering = w.createComponent(SteeringComponent.class);
        steering.init(rigidBody);
        steering.setMaxLinearSpeed(getMaxSpeed());
        EnemyRadiusProximity proximity = new EnemyRadiusProximity(steering, InGameScreen.world.physicsWorld(),
                10);
        Pursue<Vector2> pursue = new Pursue<>(steering, InGameScreen.playerInfo.playerEntity().getComponent(PlayerTargetComponent.class));
        CollisionAvoidance<Vector2> collisionAvoidance = new CollisionAvoidance<>(steering, proximity);
        PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<>(steering, 0.001f)
                .add(collisionAvoidance)
                .add(pursue);
        steering.setBehavior(prioritySteeringSB);
        enemy.add(steering);

        w.addEntity(enemy);
        return enemy;
    }
}
