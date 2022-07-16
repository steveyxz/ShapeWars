package me.partlysunny.shapewars.player;

import me.partlysunny.shapewars.player.item.Item;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.player.item.types.WeaponItem;

import java.util.HashMap;
import java.util.Map;

public class AmmoManager {

    private final Map<String, Integer> ammoMap = new HashMap<>();
    private final Map<String, Float> counter = new HashMap<>();

    public AmmoManager() {
        for (String item : ItemManager.items()) {
            Item weapon = ItemManager.getItem(item);
            if (weapon instanceof WeaponItem && ((WeaponItem) weapon).maxUses() != -1) {
                ammoMap.put(item, ((WeaponItem) weapon).maxUses());
                counter.put(item, ((WeaponItem) weapon).attackDelay());
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

    public void setAmmo(String weapon, int amount) {
        if (ammoMap.containsKey(weapon)) {
            ammoMap.put(weapon, Math.min(amount, ((WeaponItem) ItemManager.getItem(weapon)).maxUses()));
        }
    }

    public int ammoRemaining(String texture) {
        return ammoMap.get(texture);
    }

    public Map<String, Integer> ammoMap() {
        return ammoMap;
    }
}
