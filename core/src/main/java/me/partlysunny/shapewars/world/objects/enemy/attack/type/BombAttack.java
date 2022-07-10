package me.partlysunny.shapewars.world.objects.enemy.attack.type;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.BombFactory;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;

public class BombAttack implements EnemyAttack {
    private final Vector2 vec = new Vector2();
    @Override
    public float maxDistance() {
        return 60;
    }

    @Override
    public void attack(Entity enemyEntity, Entity player) {
        TransformComponent enemyPos = Mappers.transformMapper.get(enemyEntity);
        TransformComponent playerPos = Mappers.transformMapper.get(player);
        RigidBodyComponent playerBody = Mappers.bodyMapper.get(player);

        int predictionFrames = 1;
        float xDif = (playerPos.position.x + (playerBody.rigidBody().getLinearVelocity().x * predictionFrames)) - enemyPos.position.x;
        float yDif = (playerPos.position.y + playerBody.rigidBody().getLinearVelocity().y * predictionFrames) - enemyPos.position.y;

        vec.set(xDif, yDif);
        enemyPos.rotation = Util.vectorToAngle(vec);
        BombFactory.getInstance().generateBomb(enemyEntity, 3, 5, BulletRestrictions.ONLY_PLAYERS, 30, 300, "enemyThrow", "enemyBomb", "fastExplode");
    }

    @Override
    public float cooldown() {
        return 3;
    }
}
