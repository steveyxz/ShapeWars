package me.partlysunny.shapewars.world.objects.enemy;

import me.partlysunny.shapewars.world.objects.enemy.type.BasicEnemy;
import me.partlysunny.shapewars.world.objects.enemy.type.Enemy;

import java.util.HashMap;
import java.util.Map;

public class EnemyManager {

    private static final Map<String, Enemy> enemies = new HashMap<>();

    public static void registerEnemy(String id, Enemy enemy) {
        enemies.put(id, enemy);
    }

    public static Enemy getEnemy(String id) {
        return enemies.get(id);
    }

    public static void unregisterEnemy(String id) {
        enemies.remove(id);
    }

    public static void init() {
        EnemyManager.registerEnemy("basic", new BasicEnemy());
    }

}
