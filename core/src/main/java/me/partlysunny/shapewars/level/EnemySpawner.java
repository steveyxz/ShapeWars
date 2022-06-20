package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.objects.enemy.EnemyManager;
import me.partlysunny.shapewars.world.objects.enemy.type.Enemy;

import java.util.Map;

public class EnemySpawner {

    private final LevelManager levelManager;

    private ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);

    public EnemySpawner(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void spawn(Level level) {
        Map<String, Integer> entitiesToSpawn = level.enemies();
        for (String type : entitiesToSpawn.keySet()) {
            Enemy enemyType = EnemyManager.getEnemy(type);
            for (int i = 0; i < entitiesToSpawn.get(type); i++) {
                Entity enemy = enemyType.createEntity(InGameScreen.world.gameWorld());
                RigidBodyComponent body = bodyMapper.get(enemy);
                body.rigidBody().setTransform(Util.getRandomBetween(level.levelWidth() / -2, level.levelWidth() / 2), Util.getRandomBetween(level.levelHeight() / -2, level.levelHeight() / 2), body.rigidBody().getAngle());
                levelManager.addEnemy(enemy);
            }
        }
    }
}
