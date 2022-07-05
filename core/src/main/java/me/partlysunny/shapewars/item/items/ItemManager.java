package me.partlysunny.shapewars.item.items;

import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.items.armor.OldTunic;
import me.partlysunny.shapewars.item.items.utility.BasicHpPot;
import me.partlysunny.shapewars.item.items.weapons.CircleBlaster;
import me.partlysunny.shapewars.item.items.weapons.CirclePummeler;
import me.partlysunny.shapewars.item.items.weapons.WoodenStick;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.item.types.UtilityItem;
import me.partlysunny.shapewars.item.types.WeaponItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemManager {

    private static final Map<String, Item> items = new HashMap<>();
    private static final List<String> allWeapons = new ArrayList<>();
    private static final List<String> allArmors = new ArrayList<>();
    private static final List<String> allUtils = new ArrayList<>();

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

        for (Item i : items.values()) {
            if (i instanceof WeaponItem) {
                allWeapons.add(i.texture());
            }
            if (i instanceof ArmorItem) {
                allArmors.add(i.texture());
            }
            if (i instanceof UtilityItem) {
                allUtils.add(i.texture());
            }
        }
    }

    public static List<String> getAllUtilities() {
        return allUtils;
    }

    public static List<String> getAllArmors() {
        return allArmors;
    }

    public static List<String> getAllWeapons() {
        return allWeapons;
    }
}
