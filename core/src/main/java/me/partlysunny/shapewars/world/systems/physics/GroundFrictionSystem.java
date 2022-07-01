package me.partlysunny.shapewars.world.systems.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;

public class GroundFrictionSystem extends IteratingSystem {


    public GroundFrictionSystem() {
        super(Family.all(GroundFrictionComponent.class, RigidBodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RigidBodyComponent body = Mappers.bodyMapper.get(entity);
        GroundFrictionComponent friction = Mappers.frictionMapper.get(entity);
        Body rigidBody = body.rigidBody();
        Vector2 velocity = rigidBody.getLinearVelocity();
        Vector2 pos = rigidBody.getPosition();
        float lowerEnd = 0.1f;
        if (velocity.x > 0.1f) {
            rigidBody.applyLinearImpulse(-friction.friction() * deltaTime, 0, pos.x, pos.y, true);
        }
        if (velocity.x < -0.1f) {
            rigidBody.applyLinearImpulse(friction.friction() * deltaTime, 0, pos.x, pos.y, true);
        }
        if (velocity.y > 0.1f) {
            rigidBody.applyLinearImpulse(0, -friction.friction() * deltaTime, pos.x, pos.y, true);
        }
        if (velocity.y < -0.1f) {
            rigidBody.applyLinearImpulse(0, friction.friction() * deltaTime, pos.x, pos.y, true);
        }
        if (Math.abs(velocity.x) < lowerEnd) {
            rigidBody.setLinearVelocity(0, rigidBody.getLinearVelocity().y);
        }
        if (Math.abs(velocity.y) < lowerEnd) {
            rigidBody.setLinearVelocity(rigidBody.getLinearVelocity().x, 0);
        }
    }

}
