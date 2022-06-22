package me.partlysunny.shapewars.item.equipment;

import com.badlogic.ashley.core.Family;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.components.WeaponComponent;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.item.types.UtilityItem;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.GameWorld;

import java.util.ArrayList;
import java.util.List;

public class PlayerEquipment {

    private final List<Item> unlockedItems = new ArrayList<>();
    private ArmorItem armorOne = null;
    private ArmorItem armorTwo = null;
    private WeaponItem weaponOne = null;
    private WeaponItem weaponTwo = null;
    private UtilityItem util = null;
    private int activeWeaponSlot = 0;

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
        killWeaponEntities();
        respawnWeaponEntities();
    }

    public WeaponItem weaponTwo() {
        return weaponTwo;
    }

    public void setWeaponTwo(WeaponItem weaponTwo) {
        this.weaponTwo = weaponTwo;
        killWeaponEntities();
        respawnWeaponEntities();
    }

    public UtilityItem util() {
        return util;
    }

    public void setUtil(UtilityItem util) {
        this.util = util;
    }

    public int activeWeaponSlot() {
        return activeWeaponSlot;
    }

    public void setActiveWeaponSlot(int activeWeaponSlot) {
        if (activeWeaponSlot > 1 || activeWeaponSlot < 0) {
            throw new IllegalArgumentException("Must be 0 or 1");
        }
        this.activeWeaponSlot = activeWeaponSlot;
        killWeaponEntities();
        respawnWeaponEntities();
    }

    private void killWeaponEntities() {
        GameWorld world = InGameScreen.world;
        world.gameWorld().getEntitiesFor(Family.all(WeaponComponent.class).get()).forEach(entity -> world.gameWorld().removeEntity(entity));
    }

    private void respawnWeaponEntities() {
        GameWorld world = InGameScreen.world;
        WeaponItem item = activeWeaponSlot == 1 ? weaponTwo() : weaponOne();
        if (item == null) {
            return;
        }
        world.gameWorld().addEntity(item.buildEntity(world.gameWorld()));
    }

    public void tryAttack() {
        WeaponItem item = activeWeaponSlot == 1 ? weaponTwo() : weaponOne();
        if (item == null) {
            return;
        }
        item.attack(InGameScreen.playerInfo.playerEntity());
    }
}
