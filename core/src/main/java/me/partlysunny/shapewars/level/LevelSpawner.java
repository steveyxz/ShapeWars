package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.objects.enemy.EnemyManager;
import me.partlysunny.shapewars.world.objects.enemy.type.Enemy;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleEntity;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleManager;

import java.util.Map;

public class LevelSpawner {

    private final LevelManager levelManager;
    private final Vector2 vector2 = new Vector2();
    private ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);

    public LevelSpawner(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void spawn(Level level) {
        Map<String, Integer> entitiesToSpawn = level.enemies();
        for (String type : entitiesToSpawn.keySet()) {
            Enemy enemyType = EnemyManager.getEnemy(type);
            for (int i = 0; i < entitiesToSpawn.get(type); i++) {
                getPositionInLevelAwayFromCenter(15, level);
                Entity enemy = enemyType.createEntity(InGameScreen.world.gameWorld(), vector2.x, vector2.y);
                levelManager.addEnemy(enemy);
            }
        }
        Map<String, Integer> obstaclesToSpawn = level.obstacles();
        for (String type : obstaclesToSpawn.keySet()) {
            ObstacleEntity obstacle = ObstacleManager.getObstacle(type);
            for (int i = 0; i < obstaclesToSpawn.get(type); i++) {
                getPositionInLevelAwayFromCenter(15, level);
                levelManager.addObstacle(obstacle.spawnAt(vector2.x, vector2.y));
            }
        }
    }

    private Vector2 getPositionInLevelAwayFromCenter(float distance, Level level) {
        int x = Util.getRandomBetween(level.levelWidth() / -2 + GameInfo.BOUNDARY_WIDTH, level.levelWidth() / 2 - GameInfo.BOUNDARY_WIDTH);
        int y = Util.getRandomBetween(level.levelHeight() / -2 + GameInfo.BOUNDARY_WIDTH, level.levelHeight() / 2 - GameInfo.BOUNDARY_WIDTH);
        if (Math.abs(x) < distance || Math.abs(y) < distance) {
            return getPositionInLevelAwayFromCenter(distance, level);
        }
        vector2.set(x, y);
        return vector2;
    }
}
