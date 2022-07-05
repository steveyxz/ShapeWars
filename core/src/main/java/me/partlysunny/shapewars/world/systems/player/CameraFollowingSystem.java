package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.player.PlayerCameraFollowComponent;

import static me.partlysunny.shapewars.screens.InGameScreen.camera;
import static me.partlysunny.shapewars.screens.InGameScreen.cameraVelocity;

public class CameraFollowingSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerCameraFollowComponent.class, RigidBodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            PlayerCameraFollowComponent playerCamera = Mappers.cameraFollowMapper.get(e);
            RigidBodyComponent rigidBody = Mappers.bodyMapper.get(e);
            float speed = -playerCamera.speed();
            float x = rigidBody.rigidBody().getPosition().x;
            float y = rigidBody.rigidBody().getPosition().y;
            float cameraX = (camera.position.x - x) * Gdx.graphics.getDeltaTime() * speed;
            float cameraY = (camera.position.y - y) * Gdx.graphics.getDeltaTime() * speed;
            if (Math.abs(cameraX) < 0.05f) {
                cameraX = 0;
            }
            if (Math.abs(cameraY) < 0.05f) {
                cameraY = 0;
            }
            cameraVelocity.set(cameraX, cameraY);
        }
    }
}
