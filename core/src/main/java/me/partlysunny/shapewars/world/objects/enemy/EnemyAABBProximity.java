package me.partlysunny.shapewars.world.objects.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;

public class EnemyAABBProximity implements Proximity<Vector2>, QueryCallback {

    private static final AABB aabb = new AABB();
    protected Steerable<Vector2> owner;
    protected World world;
    protected ProximityCallback<Vector2> behaviorCallback;
    protected float detectionRadius;
    private int neighborCount;

    public EnemyAABBProximity(Steerable<Vector2> owner, World world, float detectionRadius) {
        this.owner = owner;
        this.world = world;
        this.detectionRadius = detectionRadius;
        this.behaviorCallback = null;
        this.neighborCount = 0;
    }

    @Override
    public Steerable<Vector2> getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Steerable<Vector2> owner) {
        this.owner = owner;
    }

    /**
     * Returns the box2d world.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Sets the box2d world.
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Returns the detection radius that is half the side of the square AABB.
     */
    public float getDetectionRadius() {
        return detectionRadius;
    }

    /**
     * Sets the detection radius that is half the side of the square AABB.
     */
    public void setDetectionRadius(float detectionRadius) {
        this.detectionRadius = detectionRadius;
    }

    @Override
    public int findNeighbors(ProximityCallback<Vector2> behaviorCallback) {
        this.behaviorCallback = behaviorCallback;
        neighborCount = 0;
        prepareAABB(aabb);
        world.QueryAABB(this, aabb.lowerX, aabb.lowerY, aabb.upperX, aabb.upperY);
        this.behaviorCallback = null;
        return neighborCount;
    }

    protected void prepareAABB(AABB aabb) {
        Vector2 position = owner.getPosition();
        aabb.lowerX = position.x - detectionRadius;
        aabb.lowerY = position.y - detectionRadius;
        aabb.upperX = position.x + detectionRadius;
        aabb.upperY = position.y + detectionRadius;
    }

    @SuppressWarnings("unchecked")
    protected Steerable<Vector2> getSteerable(Fixture fixture) {
        return (Steerable<Vector2>) fixture.getBody().getUserData();
    }

    protected boolean accept(Steerable<Vector2> steerable) {
        return true;
    }

    @Override
    public boolean reportFixture(Fixture fixture) {
        Steerable<Vector2> steerable = getSteerable(fixture);
        Entity entityWithRigidBody = InGameScreen.world.getEntityWithRigidBody(fixture.getBody());
        if (entityWithRigidBody == null) {
            return true;
        }
        boolean has = Mappers.dodgeIgnoreMapper.has(entityWithRigidBody);
        if (steerable != owner && accept(steerable) && (!has || !Mappers.dodgeIgnoreMapper.get(entityWithRigidBody).active()) && behaviorCallback.reportNeighbor(steerable)) {
            neighborCount++;
        }
        return true;
    }

    public static class AABB {
        float lowerX;
        float lowerY;
        float upperX;
        float upperY;
    }

}
