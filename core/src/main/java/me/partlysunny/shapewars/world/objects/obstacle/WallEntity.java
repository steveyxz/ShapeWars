package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.collision.WallComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;
import me.partlysunny.shapewars.world.objects.GameObject;

public class WallEntity implements GameObject {

    public static void reloadWalls(int boundaryWidth, int boundaryHeight) {
        PooledEngine w = InGameScreen.world.gameWorld();
        for (Entity wall : w.getEntitiesFor(Family.all(WallComponent.class).get())) {
            LateRemover.tagToRemove(wall);
        }
        float boundaryX = -(boundaryWidth / 2f);
        float boundaryY = -(boundaryHeight / 2f);
        //Left
        addWall(w, boundaryX, 0, GameInfo.BOUNDARY_WIDTH, boundaryHeight);
        //Right
        addWall(w, boundaryX + boundaryWidth, 0, GameInfo.BOUNDARY_WIDTH, boundaryHeight);
        //Bottom
        addWall(w, 0, boundaryY, boundaryWidth + GameInfo.BOUNDARY_WIDTH, GameInfo.BOUNDARY_WIDTH);
        //Top
        addWall(w, 0, boundaryY + boundaryHeight, boundaryWidth + GameInfo.BOUNDARY_WIDTH, GameInfo.BOUNDARY_WIDTH);
    }

    private static void addWall(PooledEngine w, float x, float y, int width, int height) {
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
        e.add(w.createComponent(WallComponent.class));
        e.add(w.createComponent(TintComponent.class));
        w.addEntity(e);
    }

    @Override
    public Entity createEntity(PooledEngine w, float originalX, float originalY) {
        reloadWalls(400, 400);
        return null;
    }

}
