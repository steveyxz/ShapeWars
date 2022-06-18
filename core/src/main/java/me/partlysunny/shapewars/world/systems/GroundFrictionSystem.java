package me.partlysunny.shapewars.world.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;

public class GroundFrictionSystem extends IteratingSystem {

    private final ComponentMapper<GroundFrictionComponent> frictionMapper = ComponentMapper.getFor(GroundFrictionComponent.class);
    private final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);

    public GroundFrictionSystem() {
        super(Family.all(GroundFrictionComponent.class, RigidBodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RigidBodyComponent body = bodyMapper.get(entity);
        GroundFrictionComponent friction = frictionMapper.get(entity);
        Body rigidBody = body.rigidBody();
        Vector2 velocity = rigidBody.getLinearVelocity();
        Vector2 pos = rigidBody.getPosition();
        if (velocity.x > 1) {
            rigidBody.applyLinearImpulse(-friction.friction(), 0, pos.x, pos.y, true);
        }
        if (velocity.x < -1) {
            rigidBody.applyLinearImpulse(friction.friction(), 0, pos.x, pos.y, true);
        }
        if (velocity.y > 1) {
            rigidBody.applyLinearImpulse(0, -friction.friction(), pos.x, pos.y, true);
        }
        if (velocity.y < -1) {
            rigidBody.applyLinearImpulse(0, friction.friction(), pos.x, pos.y, true);
        }
        if (Math.abs(velocity.x) < 1) {
            rigidBody.setLinearVelocity(0, rigidBody.getLinearVelocity().y);
        }
        if (Math.abs(velocity.y) < 1) {
            rigidBody.setLinearVelocity(rigidBody.getLinearVelocity().x, 0);
        }
    }

}
