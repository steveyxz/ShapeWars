package me.partlysunny.shapewars.world.components.player.equipment;

import me.partlysunny.shapewars.world.components.player.equipment.item.Item;
import me.partlysunny.shapewars.world.components.player.equipment.item.types.ArmorItem;
import me.partlysunny.shapewars.world.components.player.equipment.item.types.UtilityItem;
import me.partlysunny.shapewars.world.components.player.equipment.item.types.WeaponItem;

import java.util.ArrayList;
import java.util.List;

public class PlayerEquipment {

    private final List<Item> unlockedItems = new ArrayList<>();
    private ArmorItem armorOne = null;
    private ArmorItem armorTwo = null;
    private WeaponItem weaponOne = null;
    private WeaponItem weaponTwo = null;
    private UtilityItem util = null;

    public List<Item> unlockedItems() {
        return unlockedItems;
    }

    public ArmorItem armorOne() {
        return armorOne;
    }

    public void setArmorOne(ArmorItem armorOne) {
        this.armorOne = armorOne;
    }

    public ArmorItem armorTwo() {
        return armorTwo;
    }

    public void setArmorTwo(ArmorItem armorTwo) {
        this.armorTwo = armorTwo;
    }

    public WeaponItem weaponOne() {
        return weaponOne;
    }

    public void setWeaponOne(WeaponItem weaponOne) {
        this.weaponOne = weaponOne;
    }

    public WeaponItem weaponTwo() {
        return weaponTwo;
    }

    public void setWeaponTwo(WeaponItem weaponTwo) {
        this.weaponTwo = weaponTwo;
    }

    public UtilityItem util() {
        return util;
    }

    public void setUtil(UtilityItem util) {
        this.util = util;
    }
}
