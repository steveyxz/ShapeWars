package me.partlysunny.shapewars.item.items;

import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.items.armor.OldTunic;
import me.partlysunny.shapewars.item.items.utility.BasicHpPot;
import me.partlysunny.shapewars.item.items.weapons.CircleBlaster;
import me.partlysunny.shapewars.item.items.weapons.CirclePummeler;
import me.partlysunny.shapewars.item.items.weapons.WoodenStick;

import java.util.HashMap;
import java.util.Map;

public final class ItemManager {

    private static final Map<String, Item> items = new HashMap<>();

    public static void registerItem(String id, Item item) {
        items.put(id, item);
    }

    public static Item getItem(String id) {
        return items.get(id);
    }

    public static String[] items() {
        return items.keySet().toArray(new String[0]);
    }

    public static void unregisterItem(String id) {
        items.remove(id);
    }

    public static void init() {
        //Weapons
        registerItem("circleBlaster", new CircleBlaster());
        registerItem("circlePummeler", new CirclePummeler());
        registerItem("woodenStick", new WoodenStick());
        //Armor
        registerItem("oldTunic", new OldTunic());
        //Utility
        registerItem("basicHpPot", new BasicHpPot());
    }

}
