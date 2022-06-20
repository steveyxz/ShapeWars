package me.partlysunny.shapewars.level;

import java.util.Map;

public class Level {

    private final int levelWidth;
    private final int levelHeight;
    private final Map<String, Integer> enemies;
    private final int time;

    public Level(int time, int levelWidth, int levelHeight, Map<String, Integer> enemies) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.enemies = enemies;
        this.time = time;
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
