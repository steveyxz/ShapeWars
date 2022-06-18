package me.partlysunny.shapewars.world.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class PhysicsSystem extends IteratingSystem {

    private static float accumulator = 0f;

    private final World world;
    private final Array<Entity> bodiesQueue;

    private final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);
    private final ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);

    public PhysicsSystem(World world) {
        super(Family.all(TransformComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if (accumulator >= GameInfo.TIME_STEP) {
            world.step(GameInfo.TIME_STEP, 6, 2);
            accumulator -= GameInfo.TIME_STEP;

            //Entity Queue
            for (Entity entity : bodiesQueue) {
                TransformComponent tfm = transformMapper.get(entity);
                RigidBodyComponent bodyComp = bodyMapper.get(entity);
                Vector2 position = bodyComp.rigidBody().getPosition();
                tfm.position.x = position.x;
                tfm.position.y = position.y;
                tfm.rotation = bodyComp.rigidBody().getAngle() * MathUtils.radiansToDegrees;
            }
        }
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }

}
