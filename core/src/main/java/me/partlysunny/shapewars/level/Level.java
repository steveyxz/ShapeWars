package me.partlysunny.shapewars.level;

import java.util.List;
import java.util.Map;

public class Level {

    private final int levelWidth;
    private final int levelHeight;
    private final List<LevelStage> enemies;
    private final Map<String, Integer> obstacles;
    private final int time;
    private final int enemyCount;

    public Level(int time, int levelWidth, int levelHeight, List<LevelStage> enemies, Map<String, Integer> obstacles) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.enemies = enemies;
        this.time = time;
        this.obstacles = obstacles;
        int counter = 0;
        for (int i : obstacles.values()) {
            counter += i;
        }
        this.enemyCount = counter;
    }

    public int enemyCount() {
        return enemyCount;
    }

    public Map<String, Integer> obstacles() {
        return obstacles;
    }

    public int time() {
        return time;
    }

    public int levelWidth() {
        return levelWidth;
    }

    public int levelHeight() {
        return levelHeight;
    }

    public List<LevelStage> stages() {
        return enemies;
    }
}
