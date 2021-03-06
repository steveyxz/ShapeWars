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
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.collision.WallComponent;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;
import me.partlysunny.shapewars.world.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class WallEntity implements GameObject {

    private static final List<Entity> walls = new ArrayList<>();

    public static void checkWalls(int boundaryWidth, int boundaryHeight) {
        int count = 0;
        for (Entity ignored : InGameScreen.world.gameWorld().getEntitiesFor(Family.all(WallComponent.class).get()).toArray(Entity.class)) {
            count++;
        }
        if (count != 4) {
            reloadWalls(boundaryWidth, boundaryHeight);
        }
    }

    public static void reloadWalls(int boundaryWidth, int boundaryHeight) {
        PooledEngine w = InGameScreen.world.gameWorld();
        for (Entity wall : walls) {
            LateRemover.tagToRemove(wall);
        }
        walls.clear();
        float boundaryX = -(boundaryWidth / 2f);
        float boundaryY = -(boundaryHeight / 2f);
        //Right
        addWall(w, boundaryX + boundaryWidth, 0, GameInfo.BOUNDARY_WIDTH, boundaryHeight);
        //Left
        addWall(w, boundaryX, 0, GameInfo.BOUNDARY_WIDTH, boundaryHeight);
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

        e.add(w.createComponent(BulletDeleterComponent.class));

        TextureComponent texture = w.createComponent(TextureComponent.class);
        Texture finalTexture = TextureManager.getTexture("wall");
        texture.init(new TextureRegion(finalTexture));
        e.add(texture);

        TransformComponent transform = w.createComponent(TransformComponent.class);
        transform.init(width, height);
        e.add(transform);

        e.add(w.createComponent(WallComponent.class));
        e.add(w.createComponent(TintComponent.class));
        w.addEntity(e);
        walls.add(e);
    }

    @Override
    public Entity createEntity(PooledEngine w, float originalX, float originalY) {
        reloadWalls(400, 400);
        return null;
    }

}
