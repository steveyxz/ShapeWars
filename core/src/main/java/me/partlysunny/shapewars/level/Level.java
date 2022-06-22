package me.partlysunny.shapewars.level;

import java.util.Map;

public class Level {

    private final int levelWidth;
    private final int levelHeight;
    private final Map<String, Integer> enemies;
    private final Map<String, Integer> obstacles;
    private final int time;

    public Level(int time, int levelWidth, int levelHeight, Map<String, Integer> enemies, Map<String, Integer> obstacles) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.enemies = enemies;
        this.time = time;
        this.obstacles = obstacles;
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

    public Map<String, Integer> enemies() {
        return enemies;
    }
}
