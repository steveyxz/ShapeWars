package me.partlysunny.shapewars.world.objects.enemy.attack.type;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyState;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;

public class BasicBlasterAttack extends EnemyAttack {

    private Vector2 vec = new Vector2();

    public BasicBlasterAttack(int weight) {
        super(weight);
    }

    @Override
    protected float maxDistance() {
        return 90;
    }

    protected int getDamage() {
        return 2;
    }

    @Override
    protected void attack(Entity enemyEntity, Entity player) {
        TransformComponent enemyPos = Mappers.transformMapper.get(enemyEntity);
        TransformComponent playerPos = Mappers.transformMapper.get(player);
        RigidBodyComponent playerBody = Mappers.bodyMapper.get(player);

        int predictionFrames = 2;
        float xDif = (playerPos.position.x + (playerBody.rigidBody().getLinearVelocity().x * predictionFrames)) - enemyPos.position.x;
        float yDif = (playerPos.position.y + playerBody.rigidBody().getLinearVelocity().y * predictionFrames) - enemyPos.position.y;

        vec.set(xDif, yDif);
        enemyPos.rotation = Util.vectorToAngle(vec);

        Controllers.ENEMY_BLASTER.fire(enemyEntity, getDamage());

        Mappers.enemyStateMapper.get(enemyEntity).setState(EnemyState.ATTACKING);
        Mappers.enemyStateMapper.get(enemyEntity).setState(EnemyState.PURSUING, 0.5f);
    }

    @Override
    public float cooldown() {
        return 2f;
    }

}
