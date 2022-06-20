package me.partlysunny.shapewars.level;

import java.util.Map;

public class Level {

    private final int levelWidth;
    private final int levelHeight;
    private final Map<String, Integer> enemies;

    public Level(int levelWidth, int levelHeight, Map<String, Integer> enemies) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.enemies = enemies;
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
