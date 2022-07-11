package me.partlysunny.shapewars.world.objects.enemy;

import me.partlysunny.shapewars.world.objects.enemy.type.*;

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
        EnemyManager.registerEnemy("tank", new TankEnemy());
        EnemyManager.registerEnemy("blaster", new BlasterEnemy());
        EnemyManager.registerEnemy("commander", new CommanderEnemy());
        EnemyManager.registerEnemy("megaTank", new MegaTankEnemy());
        EnemyManager.registerEnemy("bomber", new BomberEnemy());
        EnemyManager.registerEnemy("sniper", new SniperEnemy());
        EnemyManager.registerEnemy("general", new GeneralEnemy());
    }

}
