package me.partlysunny.shapewars.level;

import java.util.Map;

public class LevelStage {

    private final Map<String, Integer> enemies;
    private final int enemyCount;
    private Level parent = null;

    public LevelStage(Map<String, Integer> enemies) {
        this.enemies = enemies;
        int total = 0;
        for (int i : enemies.values()) {
            total += i;
        }
        this.enemyCount = total;
    }

    public Level parent() {
        return parent;
    }

    public void setParent(Level parent) {
        this.parent = parent;
    }

    public Map<String, Integer> enemies() {
        return enemies;
    }

    public int enemyCount() {
        return enemyCount;
    }
}
