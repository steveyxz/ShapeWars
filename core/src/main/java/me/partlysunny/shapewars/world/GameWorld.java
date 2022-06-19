package me.partlysunny.shapewars.world;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import me.partlysunny.shapewars.util.ContactDispatcher;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.bullets.controllers.Controllers;
import me.partlysunny.shapewars.world.systems.mechanics.BulletUpdaterSystem;
import me.partlysunny.shapewars.world.systems.mechanics.HealthSystem;
import me.partlysunny.shapewars.world.systems.physics.GroundFrictionSystem;
import me.partlysunny.shapewars.world.systems.physics.PhysicsSystem;
import me.partlysunny.shapewars.world.systems.player.*;
import me.partlysunny.shapewars.world.systems.render.AnimationSystem;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

public class GameWorld {

    private final PooledEngine gameWorld;
    private final World physicsWorld;

    private ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);

    public GameWorld(Stage stage) {
        this.physicsWorld = new World(new Vector2(0, 0), true);
        ContactDispatcher.init(physicsWorld);
        Controllers.init();
        this.gameWorld = new PooledEngine(100, 1000, 1000, 10000);
        //Mechanics
        gameWorld.addSystem(new HealthSystem());
        gameWorld.addSystem(new BulletUpdaterSystem());
        //Physics
        gameWorld.addSystem(new PhysicsSystem(physicsWorld));
        gameWorld.addSystem(new GroundFrictionSystem());
        //Player systems
        gameWorld.addSystem(new CameraFollowingSystem());
        gameWorld.addSystem(new PlayerMovementSystem());
        gameWorld.addSystem(new PlayerItemSystem());
        gameWorld.addSystem(new PlayerFaceMouseSystem());
        gameWorld.addSystem(new PlayerAttackingSystem());
        //Rendering systems
        gameWorld.addSystem(new AnimationSystem());
        gameWorld.addSystem(new TextureRenderingSystem(stage.getBatch()));
    }

    public Entity getEntityWithRigidBody(Body b) {
        for (Entity entity : gameWorld.getEntitiesFor(Family.all(RigidBodyComponent.class).get())) {
            RigidBodyComponent rigidBodyComponent = bodyMapper.get(entity);
            if (rigidBodyComponent.rigidBody().equals(b)) {
                return entity;
            }
        }
        return null;
    }

    public PooledEngine gameWorld() {
        return gameWorld;
    }

    public World physicsWorld() {
        return physicsWorld;
    }
}
