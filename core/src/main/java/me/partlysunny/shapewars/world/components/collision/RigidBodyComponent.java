package me.partlysunny.shapewars.world.components.collision;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.screens.InGameScreen;

public class RigidBodyComponent implements Component, Pool.Poolable {

    private Body rigidBody;

    public RigidBodyComponent() {
    }

    public void initBody(float x, float y, float rotation, FixtureDef fixture, BodyDef.BodyType type) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);

        rigidBody = InGameScreen.world.physicsWorld().createBody(bodyDef);

        Fixture f = rigidBody.createFixture(fixture);
        fixture.shape.dispose();
    }

    public Body rigidBody() {
        return rigidBody;
    }

    @Override
    public void reset() {
        rigidBody = null;
    }
}
