package me.partlysunny.shapewars.world;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import me.partlysunny.shapewars.world.systems.*;

public class GameWorld {

    private final PooledEngine gameWorld;
    private final World physicsWorld;

    public GameWorld(Stage stage) {
        this.physicsWorld = new World(new Vector2(0, 0), true);
        this.gameWorld = new PooledEngine(100, 1000, 1000, 10000);
        gameWorld.addSystem(new CameraFollowingSystem());
        gameWorld.addSystem(new GroundFrictionSystem());
        gameWorld.addSystem(new PlayerMovementSystem());
        gameWorld.addSystem(new AnimationSystem());
        gameWorld.addSystem(new PhysicsSystem(physicsWorld));
        gameWorld.addSystem(new TextureRenderingSystem(stage.getBatch()));
    }

    public PooledEngine gameWorld() {
        return gameWorld;
    }

    public World physicsWorld() {
        return physicsWorld;
    }
}
