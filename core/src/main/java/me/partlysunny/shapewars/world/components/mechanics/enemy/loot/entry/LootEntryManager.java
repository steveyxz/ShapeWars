package me.partlysunny.shapewars.world.components.mechanics.enemy.loot.entry;

import java.util.HashMap;
import java.util.Map;

public class LootEntryManager {

    private static final Map<String, Entry> entryTypes = new HashMap<>();

    public static void registerEntry(String id, Entry e) {
        entryTypes.put(id, e);
    }

    public static void unregisterEntry(String id) {
        entryTypes.remove(id);
    }

    public static Entry getEntry(String id) {
        return entryTypes.get(id);
    }

    public static String[] getEntryKeys() {
        return entryTypes.keySet().toArray(new String[0]);
    }

}
