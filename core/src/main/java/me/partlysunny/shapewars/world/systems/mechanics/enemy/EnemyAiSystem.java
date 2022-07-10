package me.partlysunny.shapewars.world.systems.mechanics.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.ai.SteeringComponent;
import me.partlysunny.shapewars.world.components.ai.WanderComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.enemy.EnemyState;
import me.partlysunny.shapewars.world.components.enemy.EnemyStateComponent;

public class EnemyAiSystem extends IteratingSystem {

    public EnemyAiSystem() {
        super(Family.all(SteeringComponent.class, RigidBodyComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SteeringComponent steering = Mappers.steeringMapper.get(entity);
        WanderComponent wander = Mappers.wanderMapper.get(entity);
        EnemyStateComponent state = Mappers.enemyStateMapper.get(entity);

        Entity player = InGameScreen.playerInfo.playerEntity();
        TransformComponent playerTransform = Mappers.transformMapper.get(player);
        TransformComponent entityTransform = Mappers.transformMapper.get(entity);

        float dist = Util.getDistanceBetween(playerTransform, entityTransform);
        if (dist > steering.viewRange()) {
            if (state.state() != EnemyState.WANDERING) {
                state.setState(EnemyState.WANDERING, 0);
            }
        } else if (state.state() == EnemyState.WANDERING) {
            state.setState(EnemyState.MOVING, 0);
        }

        if (steering.behavior() != null) {
            if (state.state() == EnemyState.MOVING) {
                steering.behavior().calculateSteering(steering.steeringOutput());
                applySteering(steering, entity, deltaTime);
            } else if (state.state() == EnemyState.WANDERING) {
                wander.behavior().calculateSteering(wander.steeringOutput());
                applySteering(wander, entity, deltaTime);
            }
        }
    }

    private void applySteering(SteeringComponent steering, Entity e, float deltaTime) {
        RigidBodyComponent body = Mappers.bodyMapper.get(e);
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
