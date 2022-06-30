package me.partlysunny.shapewars.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import me.partlysunny.shapewars.bullets.controllers.Controllers;
import me.partlysunny.shapewars.util.classes.ContactDispatcher;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.systems.mechanics.BulletUpdaterSystem;
import me.partlysunny.shapewars.world.systems.mechanics.EnemyAiSystem;
import me.partlysunny.shapewars.world.systems.mechanics.EnemyAttackSystem;
import me.partlysunny.shapewars.world.systems.mechanics.HealthSystem;
import me.partlysunny.shapewars.world.systems.physics.GroundFrictionSystem;
import me.partlysunny.shapewars.world.systems.physics.PhysicsSystem;
import me.partlysunny.shapewars.world.systems.player.*;
import me.partlysunny.shapewars.world.systems.render.AnimationSystem;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import java.util.HashMap;
import java.util.Map;

public class GameWorld {

    private final PooledEngine gameWorld;
    private final World physicsWorld;

    private Map<Body, Entity> bodyCache = new HashMap<>();

    public GameWorld(Stage stage) {
        this.physicsWorld = new World(new Vector2(0, 0), true);
        ContactDispatcher.init(physicsWorld);
        Controllers.init();
        this.gameWorld = new PooledEngine(100, 1000, 1000, 10000000);
        //Mechanics
        gameWorld.addSystem(new HealthSystem());
        gameWorld.addSystem(new BulletUpdaterSystem());
        gameWorld.addSystem(new EnemyAiSystem());
        gameWorld.addSystem(new EnemyAttackSystem());
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
        if (bodyCache.containsKey(b)) {
            if (bodyCache.get(b) != null) {
                return bodyCache.get(b);
            } else {
                bodyCache.remove(b);
            }
        }
        for (Entity entity : gameWorld.getEntitiesFor(Family.all(RigidBodyComponent.class).get())) {
            RigidBodyComponent rigidBodyComponent = Mappers.bodyMapper.get(entity);
            if (rigidBodyComponent.rigidBody().equals(b)) {
                gameWorld.addEntityListener(new EntityListener() {
                    @Override
                    public void entityAdded(Entity entity) {
                    }

                    @Override
                    public void entityRemoved(Entity e) {
                        if (entity.equals(e)) {
                            bodyCache.remove(b);
                        }
                    }
                });
                bodyCache.put(b, entity);
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
