package me.partlysunny.shapewars.world.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class PlayerTargetComponent extends SteerableAdapter<Vector2> implements Component, Pool.Poolable {

    private RigidBodyComponent playerBody;

    public void init(RigidBodyComponent body) {
        playerBody = body;
    }

    @Override
    public Vector2 getPosition() {
        return playerBody.rigidBody().getPosition();
    }

    @Override
    public float getOrientation() {
        return playerBody.rigidBody().getAngle();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return playerBody.rigidBody().getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return playerBody.rigidBody().getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return playerBody.rigidBody().getFixtureList().get(0).getShape().getRadius();
    }

    @Override
    public Location<Vector2> newLocation() {
        return new PlayerTargetComponent();
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
    public void reset() {
        playerBody = null;
    }
}
