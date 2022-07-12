package me.partlysunny.shapewars.world.objects.enemy.attack.type.melee;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.enemy.EnemyState;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;

public class BasicAttack implements EnemyAttack {

    private Vector2 vel = new Vector2();


    @Override
    public float maxDistance() {
        return 15;
    }

    protected float dashFactor() {
        return 1;
    }

    protected int getDamage() {
        return 4;
    }

    protected float dashDuration() {
        return 0.5f;
    }

    @Override
    public void attack(Entity enemyEntity, Entity player) {
        RigidBodyComponent enemyBody = Mappers.bodyMapper.get(enemyEntity);
        RigidBodyComponent playerBody = Mappers.bodyMapper.get(player);
        //Dash at the player
        float dashFactor = dashFactor();
        float x = (playerBody.rigidBody().getPosition().x - enemyBody.rigidBody().getPosition().x) * dashFactor;
        float y = (playerBody.rigidBody().getPosition().y - enemyBody.rigidBody().getPosition().y) * dashFactor;
        vel.set(x, y);
        enemyBody.rigidBody().setLinearVelocity(vel.x, vel.y);
        Mappers.enemyMeleeDamageMapper.get(enemyEntity).setDamage(getDamage());
        Mappers.enemyStateMapper.get(enemyEntity).setState(EnemyState.ATTACKING);
        Mappers.enemyStateMapper.get(enemyEntity).setState(EnemyState.MOVING, dashDuration());
    }

    @Override
    public float cooldown() {
        return 2f;
    }
}
