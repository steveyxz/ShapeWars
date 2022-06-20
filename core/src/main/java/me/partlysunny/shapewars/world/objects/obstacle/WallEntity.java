package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.TextureManager;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.objects.GameObject;

public class WallEntity implements GameObject {

    @Override
    public void createEntity(PooledEngine w) {
        float boundaryX = -200;
        float boundaryY = -200;
        int boundaryWidth = 400;
        int boundaryHeight = 400;
        //Left
        addWall(w, boundaryX, 0, GameInfo.BOUNDARY_WIDTH, boundaryHeight);
        //Right
        addWall(w, boundaryX + boundaryWidth, 0, GameInfo.BOUNDARY_WIDTH, boundaryHeight);
        //Bottom
        addWall(w, 0, boundaryY, boundaryWidth + GameInfo.BOUNDARY_WIDTH, GameInfo.BOUNDARY_WIDTH);
        //Top
        addWall(w, 0, boundaryY + boundaryHeight, boundaryWidth + GameInfo.BOUNDARY_WIDTH, GameInfo.BOUNDARY_WIDTH);
    }

    private void addWall(PooledEngine w, float x, float y, int width, int height) {
        Entity e = w.createEntity();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);
        FixtureDef def = new FixtureDef();
        def.friction = 0.5f;
        def.shape = shape;
        RigidBodyComponent rigidBody = w.createComponent(RigidBodyComponent.class);
        rigidBody.initBody(x, y, 0, def, BodyDef.BodyType.StaticBody, GameInfo.BOUNDARY_WIDTH / 2f);
        e.add(rigidBody);
        TextureComponent texture = w.createComponent(TextureComponent.class);
        Texture finalTexture = TextureManager.getTexture("wall");
        e.add(w.createComponent(BulletDeleterComponent.class));
        texture.init(new TextureRegion(finalTexture));
        TransformComponent scale = w.createComponent(TransformComponent.class);
        scale.init(width, height);
        e.add(scale);
        e.add(texture);
        w.addEntity(e);
    }

}
