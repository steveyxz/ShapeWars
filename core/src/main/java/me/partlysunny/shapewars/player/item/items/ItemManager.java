package me.partlysunny.shapewars.player.item.items;

import me.partlysunny.shapewars.player.item.Item;
import me.partlysunny.shapewars.player.item.items.armor.InfusedBoots;
import me.partlysunny.shapewars.player.item.items.armor.OldTunic;
import me.partlysunny.shapewars.player.item.items.armor.PaddedGreaves;
import me.partlysunny.shapewars.player.item.items.armor.SpikedHelm;
import me.partlysunny.shapewars.player.item.items.utility.BasicHpPot;
import me.partlysunny.shapewars.player.item.items.weapons.*;
import me.partlysunny.shapewars.player.item.types.ArmorItem;
import me.partlysunny.shapewars.player.item.types.UtilityItem;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;

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
        registerItem("woodenStick", new WoodenStick());
        registerItem("circlePummeler", new CirclePummeler());
        registerItem("bombLauncher", new BombLauncher());
        registerItem("bombLobber", new BombLobber());
        registerItem("triangleShotgun", new TriangleShotgun());
        //Armor
        registerItem("oldTunic", new OldTunic());
        registerItem("paddedGreaves", new PaddedGreaves());
        registerItem("spikedHelm", new SpikedHelm());
        registerItem("infusedBoots", new InfusedBoots());
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
        return getAllArmors(false);
    }

    public static List<String> getAllArmors(boolean onlyLocked) {
        if (!onlyLocked) {
            return allArmors;
        }
        List<String> returned = new ArrayList<>(allArmors);
        List<String> remove = new ArrayList<>();
        for (int i = 0; i < returned.size(); i++) {
            String s = returned.get(i);
            if (InGameScreen.playerInfo.equipment().unlockedArmors().contains(s)) {
                remove.add(s);
            }
        }
        for (String s : remove) {
            returned.remove(s);
        }
        return returned;
    }

    public static List<String> getAllWeapons() {
        return getAllWeapons(false);
    }

    public static List<String> getAllWeapons(boolean onlyLocked) {
        if (!onlyLocked) {
            return allWeapons;
        }
        List<String> returned = new ArrayList<>(allWeapons);
        List<String> remove = new ArrayList<>();
        for (int i = 0; i < returned.size(); i++) {
            String s = returned.get(i);
            if (InGameScreen.playerInfo.equipment().unlockedWeapons().contains(s)) {
                remove.add(s);
            }
        }
        for (String s : remove) {
            returned.remove(s);
        }
        return returned;
    }
}
