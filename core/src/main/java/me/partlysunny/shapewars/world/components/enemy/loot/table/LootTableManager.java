package me.partlysunny.shapewars.world.components.enemy.loot.table;

import java.util.HashMap;
import java.util.Map;

public class LootTableManager {

    private static final Map<String, CustomLootTable> tables = new HashMap<>();

    public static void registerTable(String id, CustomLootTable e) {
        tables.put(id, e);
    }

    public static void unregisterTable(String id) {
        tables.remove(id);
    }

    public static CustomLootTable getTable(String id) {
        return tables.get(id);
    }

}
