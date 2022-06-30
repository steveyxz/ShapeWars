package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.PositionSet;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.objects.enemy.EnemyManager;
import me.partlysunny.shapewars.world.objects.enemy.EnemySpawnObject;
import me.partlysunny.shapewars.world.objects.enemy.type.Enemy;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleEntity;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleManager;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import java.util.Map;

public class LevelSpawner {

    private final LevelManager levelManager;
    private final Vector2 vector2 = new Vector2();

    public LevelSpawner(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void spawnObstacles(Level level) {
        Map<String, Integer> obstaclesToSpawn = level.obstacles();
        for (String type : obstaclesToSpawn.keySet()) {
            ObstacleEntity obstacle = ObstacleManager.getObstacle(type);
            for (int i = 0; i < obstaclesToSpawn.get(type); i++) {
                getPositionInLevelAwayFromCenter(15, level);
                levelManager.addObstacle(obstacle.spawnAt(vector2.x, vector2.y));
            }
        }
    }

    public void spawnIndicators(PositionSet positions) {
        PooledEngine engine = InGameScreen.world.gameWorld();
        EnemySpawnObject obj = new EnemySpawnObject();
        for (Vector2 v : positions.positions()) {
            obj.createEntity(engine, v.x, v.y);
        }
    }

    public void spawn(LevelStage stage, PositionSet positions) {
        Map<String, Integer> entitiesToSpawn = stage.enemies();
        int counter = 0;
        for (String type : entitiesToSpawn.keySet()) {
            Enemy enemyType = EnemyManager.getEnemy(type);
            Integer entityCount = entitiesToSpawn.get(type);
            for (int i = 0; i < entityCount; i++) {
                Vector2 spawnPos = positions.positions().get(counter + i);
                Entity enemy = enemyType.createEntity(InGameScreen.world.gameWorld(), spawnPos.x, spawnPos.y);
                levelManager.addEnemy(enemy);
                ParticleEffectManager.startEffect("enemySpawnIn", (int) TextureRenderingSystem.metersToPixels(spawnPos.x), (int) TextureRenderingSystem.metersToPixels(spawnPos.y), 50);
            }
            counter += entityCount;
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
