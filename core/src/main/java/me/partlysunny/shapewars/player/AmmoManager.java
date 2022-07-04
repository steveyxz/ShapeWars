package me.partlysunny.shapewars.player;

import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.items.ItemManager;
import me.partlysunny.shapewars.item.types.WeaponItem;

import java.util.HashMap;
import java.util.Map;

public class AmmoManager {

    private Map<String, Integer> ammoMap = new HashMap<>();
    private Map<String, Float> counter = new HashMap<>();

    public AmmoManager() {
        for (String item : ItemManager.items()) {
            Item weapon = ItemManager.getItem(item);
            if (weapon instanceof WeaponItem) {
                if (((WeaponItem) weapon).maxUses() != -1) {
                    ammoMap.put(item, ((WeaponItem) weapon).maxUses());
                    counter.put(item, ((WeaponItem) weapon).attackDelay());
                }
            }
        }
    }

    public void update(float delta, String weapon) {
        Float current = counter.get(weapon);
        if (current != null) {
            counter.put(weapon, current - delta);
            if (counter.get(weapon) < 0) {
                counter.put(weapon, 0f);
                if (ammoMap.get(weapon) < ((WeaponItem) ItemManager.getItem(weapon)).maxUses()) {
                    ammoMap.put(weapon, ammoMap.get(weapon) + 1);
                    counter.put(weapon, ((WeaponItem) ItemManager.getItem(weapon)).usesRegenRate());
                }
            }
        }
    }

    public boolean canUse(String weapon) {
        WeaponItem w = (WeaponItem) ItemManager.getItem(weapon);
        if (w.maxUses() == -1) {
            return true;
        }
        return ammoMap.get(weapon) > 0;
    }

    public void useAmmo(String weapon) {
        if (ammoMap.containsKey(weapon)) {
            ammoMap.put(weapon, ammoMap.get(weapon) - 1);
        }
    }

    public int ammoRemaining(String texture) {
        return ammoMap.get(texture);
    }
}
