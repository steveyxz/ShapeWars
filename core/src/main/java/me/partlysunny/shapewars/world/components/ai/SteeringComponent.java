package me.partlysunny.shapewars.world.components.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.util.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;

public class SteeringComponent implements Component, Pool.Poolable, Steerable<Vector2> {

    private RigidBodyComponent entityBody = null;
    private boolean tagged = false;
    private float maxLinearSpeed, maxLinearAcceleration;
    private float maxAngularSpeed, maxAngularAcceleration;
    private float zeroLinearSpeedThreshold;

    private SteeringBehavior<Vector2> behavior;
    private SteeringAcceleration<Vector2> steeringOutput;

    public void init(RigidBodyComponent rigidBody) {
        this.entityBody = rigidBody;

        this.maxLinearSpeed = 10;
        this.maxLinearAcceleration = 100;
        this.maxAngularSpeed = 30;
        this.maxAngularAcceleration = 5;
        this.zeroLinearSpeedThreshold = 0.1f;

        this.tagged = false;

        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.entityBody.rigidBody().setUserData(this);
    }

    @Override
    public void reset() {
        entityBody = null;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return entityBody.rigidBody().getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return entityBody.rigidBody().getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return entityBody.rigidBody().getFixtureList().get(0).getShape().getRadius();
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return zeroLinearSpeedThreshold;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        this.zeroLinearSpeedThreshold = value;
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return entityBody.rigidBody().getPosition();
    }

    @Override
    public float getOrientation() {
        return entityBody.rigidBody().getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        entityBody.rigidBody().getPosition().setAngleDeg(orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Util.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Util.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new SteeringComponent();
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public RigidBodyComponent entityBody() {
        return entityBody;
    }

    public SteeringAcceleration<Vector2> steeringOutput() {
        return steeringOutput;
    }

    public void setSteeringOutput(SteeringAcceleration<Vector2> steeringOutput) {
        this.steeringOutput = steeringOutput;
    }

    public SteeringBehavior<Vector2> behavior() {
        return behavior;
    }
}
