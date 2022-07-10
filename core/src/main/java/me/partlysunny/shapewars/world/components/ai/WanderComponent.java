package me.partlysunny.shapewars.world.components.ai;

import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.player.PlayerTargetComponent;
import me.partlysunny.shapewars.world.objects.enemy.EnemyRadiusProximity;

public class WanderComponent extends SteeringComponent {

    @Override
    public void init(RigidBodyComponent rigidBody, float v) {
        super.init(rigidBody, v);
        this.setMaxLinearSpeed(getMaxLinearSpeed() / 3f);
        EnemyRadiusProximity proximity = new EnemyRadiusProximity(this, InGameScreen.world.physicsWorld(),
                10);
        CollisionAvoidance<Vector2> collisionAvoidance = new CollisionAvoidance<>(this, proximity);
        PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<>(this, 0.001f)
                .add(collisionAvoidance);
        Pursue<Vector2> pursue = new Pursue<>(this, InGameScreen.playerInfo.playerEntity().getComponent(PlayerTargetComponent.class));
        prioritySteeringSB.add(pursue);
        behavior = prioritySteeringSB;
    }
}
