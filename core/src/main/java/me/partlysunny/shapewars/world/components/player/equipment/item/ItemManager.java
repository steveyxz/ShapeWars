package me.partlysunny.shapewars.world.components.player.equipment.item;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static final Map<String, Item> items = new HashMap<>();

    public static void registerItem(String id, Item item) {
        items.put(id, item);
    }

    public static Item getItem(String id) {
        return items.get(id);
    }

    public static void unregisterItem(String id) {
        items.remove(id);
    }

    public static void init() {

    }

}
