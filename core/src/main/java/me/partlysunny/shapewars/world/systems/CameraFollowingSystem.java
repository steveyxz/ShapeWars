package me.partlysunny.shapewars.world.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import me.partlysunny.shapewars.world.components.PlayerCameraFollowComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;

import static me.partlysunny.shapewars.screens.InGameScreen.camera;
import static me.partlysunny.shapewars.screens.InGameScreen.cameraVelocity;

public class CameraFollowingSystem extends EntitySystem {

    private ComponentMapper<PlayerCameraFollowComponent> cameraFollowMapper = ComponentMapper.getFor(PlayerCameraFollowComponent.class);
    private ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerCameraFollowComponent.class, RigidBodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            PlayerCameraFollowComponent playerCamera = cameraFollowMapper.get(e);
            RigidBodyComponent rigidBody = bodyMapper.get(e);
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
