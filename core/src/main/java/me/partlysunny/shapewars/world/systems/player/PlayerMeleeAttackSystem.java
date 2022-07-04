package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.player.PlayerMeleeAttackComponent;

public class PlayerMeleeAttackSystem extends IteratingSystem {
    public PlayerMeleeAttackSystem() {
        super(Family.all(PlayerMeleeAttackComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerMeleeAttackComponent melee = Mappers.playerMeleeAttackMapper.get(entity);
        RigidBodyComponent rigidBody = Mappers.bodyMapper.get(entity);

        rigidBody.rigidBody().setLinearVelocity(melee.xMovement(), melee.yMovement());

        melee.update(deltaTime);
    }
}
