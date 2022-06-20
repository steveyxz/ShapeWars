package me.partlysunny.shapewars.world.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleEntity;

public class EntityManager {

    private final PooledEngine w;

    public EntityManager(PooledEngine w) {
        this.w = w;
    }

    public static Entity addObstacle(PooledEngine w, ObstacleEntity oe) {
        Entity e = w.createEntity();
        PolygonShape shape = new PolygonShape();
        int width = (int) oe.width();
        int height = (int) oe.height();
        shape.setAsBox(width / 2f, height / 2f);
        FixtureDef def = new FixtureDef();
        def.friction = 0.2f;
        def.shape = shape;
        RigidBodyComponent rigidBody = w.createComponent(RigidBodyComponent.class);
        float y = oe.y();
        float x = oe.x();
        rigidBody.initBody(x, y, oe.angle(), def, BodyDef.BodyType.DynamicBody, width / 2f);
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
        w.addEntity(e);
        return e;
    }

    public PooledEngine w() {
        return w;
    }

    public void registerEntity(GameObject g) {
        g.createEntity(w);
    }
}
