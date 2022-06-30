package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.RandomList;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;

public class EnemyAttackSelector {

    private RandomList<EnemyAttack> possibleAttacks = new RandomList<>();

    public EnemyAttackSelector add(EnemyAttack e) {
        possibleAttacks.add(e, e.weight());
        return this;
    }

    public EnemyAttack tryAttack(Entity asEntity) {
        Entity player = InGameScreen.playerInfo.playerEntity();
        RigidBodyComponent playerPos = Mappers.bodyMapper.get(player);
        RigidBodyComponent enemyPos = Mappers.bodyMapper.get(asEntity);
        boolean isPossible = false;
        float xDist = enemyPos.rigidBody().getPosition().x - playerPos.rigidBody().getPosition().x;
        float yDist = enemyPos.rigidBody().getPosition().y - playerPos.rigidBody().getPosition().y;
        double finalDist = Math.sqrt(xDist * xDist + yDist * yDist);
        for (RandomList<EnemyAttack>.RandomCollectionObject<EnemyAttack> entry : possibleAttacks) {
            if (finalDist < entry.getObject().maxDistance()) {
                isPossible = true;
                break;
            }
        }
        if (!isPossible) {
            return null;
        }
        while (true) {
            EnemyAttack chosenAttack = possibleAttacks.raffle();
            if (finalDist < chosenAttack.maxDistance()) {
                chosenAttack.attack(asEntity, player);
                return chosenAttack;
            }
        }
    }

}
