package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;

import java.util.ArrayList;
import java.util.List;

public class EnemyAttackSelector {

    private final List<EnemyAttack> possibleAttacks = new ArrayList<>();

    public EnemyAttackSelector add(EnemyAttack e) {
        possibleAttacks.add(e);
        return this;
    }

    public EnemyAttack tryAttack(Entity asEntity, EnemyAttack selectedAttack) {
        Entity player = InGameScreen.playerInfo.playerEntity();
        RigidBodyComponent playerPos = Mappers.bodyMapper.get(player);
        RigidBodyComponent enemyPos = Mappers.bodyMapper.get(asEntity);
        float xDist = enemyPos.rigidBody().getPosition().x - playerPos.rigidBody().getPosition().x;
        float yDist = enemyPos.rigidBody().getPosition().y - playerPos.rigidBody().getPosition().y;
        double finalDist = Math.sqrt(xDist * xDist + yDist * yDist);
        if (finalDist > selectedAttack.maxDistance()) {
            return null;
        }
        while (true) {
            if (finalDist < selectedAttack.maxDistance()) {
                selectedAttack.attack(asEntity, player);
                return selectedAttack;
            }
        }
    }

    public List<EnemyAttack> possibleAttacks() {
        return possibleAttacks;
    }
}
