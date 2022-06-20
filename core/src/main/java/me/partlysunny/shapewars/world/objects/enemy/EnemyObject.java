package me.partlysunny.shapewars.world.objects.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.factories.Box2DFactory;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.ai.SteeringComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.player.PlayerTargetComponent;
import me.partlysunny.shapewars.world.objects.GameObject;

public class EnemyObject implements GameObject {
    @Override
    public void createEntity(PooledEngine w) {
        Entity enemy = w.createEntity();
        int width = 6;
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f);
        shape.setPosition(new Vector2(0, 0));
        FixtureDef def = Box2DFactory.getInstance(InGameScreen.world.physicsWorld()).generateFixture(Box2DFactory.Material.LIGHT, shape);
        //Set components
        RigidBodyComponent rigidBody = w.createComponent(RigidBodyComponent.class);
        rigidBody.initBody(0, 0, 0, def, BodyDef.BodyType.DynamicBody, width / 2f, true);
        enemy.add(rigidBody);
        TextureComponent texture = w.createComponent(TextureComponent.class);
        texture.init(new TextureRegion(TextureManager.getTexture("enemy")));
        TransformComponent scale = w.createComponent(TransformComponent.class);
        scale.init(width, width);
        enemy.add(scale);
        enemy.add(texture);
        enemy.add(w.createComponent(BulletDeleterComponent.class));
        HealthComponent health = w.createComponent(HealthComponent.class);
        health.setHealth(100);
        enemy.add(health);
        SteeringComponent steering = w.createComponent(SteeringComponent.class);
        steering.init(rigidBody);

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
    }
}
