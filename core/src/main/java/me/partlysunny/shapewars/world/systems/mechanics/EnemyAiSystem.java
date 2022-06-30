package me.partlysunny.shapewars.world.systems.mechanics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.ai.SteeringComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class EnemyAiSystem extends IteratingSystem {

    private ComponentMapper<SteeringComponent> steeringMapper = ComponentMapper.getFor(SteeringComponent.class);


    public EnemyAiSystem() {
        super(Family.all(SteeringComponent.class, RigidBodyComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SteeringComponent steering = steeringMapper.get(entity);

        if (steering.behavior() != null) {
            steering.behavior().calculateSteering(steering.steeringOutput());
            applySteering(steering, entity, deltaTime);
        }
    }

    private void applySteering(SteeringComponent steering, Entity e, float deltaTime) {
        RigidBodyComponent body = Mappers.bodyMapper.get(e);
        TransformComponent transform = Mappers.transformMapper.get(e);
        boolean accelerations = false;

        if (!steering.steeringOutput().isZero()) {
            Vector2 force = steering.steeringOutput().linear.scl(deltaTime);
            body.rigidBody().applyForceToCenter(force, true);
            accelerations = true;
        }

        if (steering.steeringOutput().angular != 0) {
            body.rigidBody().applyTorque(steering.steeringOutput().angular * deltaTime, true);
            accelerations = true;
        } else {
            Vector2 linVel = body.rigidBody().getLinearVelocity();
            if (!linVel.isZero()) {
                float newOrientation = steering.vectorToAngle(linVel);
                body.rigidBody().setAngularVelocity((newOrientation - body.rigidBody().getAngularVelocity()) * deltaTime);
                body.rigidBody().setTransform(body.rigidBody().getPosition(), newOrientation);
            }
        }

        if (accelerations) {
            //Linear
            Vector2 velocity = body.rigidBody().getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if (currentSpeedSquare > steering.getMaxLinearSpeed() * steering.getMaxLinearSpeed()) {
                Vector2 scl = velocity.scl(steering.getMaxLinearSpeed() / (float) Math.sqrt(currentSpeedSquare));
                body.rigidBody().setLinearVelocity(scl);
            }

            //Angular
            if (body.rigidBody().getAngularVelocity() > steering.getMaxAngularSpeed()) {
                body.rigidBody().setAngularVelocity(steering.getMaxAngularSpeed());
            }
        }
    }
}
