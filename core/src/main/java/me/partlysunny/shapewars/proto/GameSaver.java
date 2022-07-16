package me.partlysunny.shapewars.proto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.protobuf.InvalidProtocolBufferException;
import me.partlysunny.shapewars.level.LevelManager;
import me.partlysunny.shapewars.player.equipment.PlayerEquipment;
import me.partlysunny.shapewars.player.item.Item;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.player.item.types.ArmorItem;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;

import java.util.ArrayList;
import java.util.List;

public class GameSaver {

    public static void save() {
        me.partlysunny.shapewars.player.PlayerInfo customInfo = InGameScreen.playerInfo;
        PlayerEquipment customEquipment = customInfo.equipment();
        LevelManager levelManager = InGameScreen.levelManager;
        List<PlayerInfo.AmmoManager.GunInfo> gunInfos = new ArrayList<>();
        List<PlayerInfo.PlayerEquipment.UtilInventory.UtilItem> utilItems = new ArrayList<>();
        customEquipment.utilities().forEach((key, value) -> utilItems.add(PlayerInfo.PlayerEquipment.UtilInventory.UtilItem.newBuilder().setType(key).setAmount(value).build()));
        customInfo.ammoManager().ammoMap().forEach((key, value) -> gunInfos.add(PlayerInfo.AmmoManager.GunInfo.newBuilder().setType(key).setRemaining(value).build()));
        PlayerInfo info = PlayerInfo.newBuilder()
                .setInGameTime(customInfo.inGameTime)
                .setHealth(customInfo.health())
                .setMaxHealth(customInfo.maxHealth())
                .setMoney(customInfo.money())
                .setLevel(levelManager.currentLevel())
                .setAmmoManager(
                        PlayerInfo.AmmoManager.newBuilder()
                                .addAllAmmoRemaining(gunInfos)
                                .build()
                )
                .setEquipment(
                        PlayerInfo.PlayerEquipment.newBuilder()
                                .setArmor1(customEquipment.armorOneTexture())
                                .setArmor2(customEquipment.armorTwoTexture())
                                .setWeapon1(customEquipment.weaponOneTexture())
                                .setWeapon2(customEquipment.weaponTwoTexture())
                                .setUnlockItems(
                                        PlayerInfo.PlayerEquipment.UnlockedItems.newBuilder()
                                                .addAllArmors(customEquipment.unlockedArmors())
                                                .addAllWeapons(customEquipment.unlockedWeapons())
                                                .build()
                                )
                                .setUtilities(
                                        PlayerInfo.PlayerEquipment.UtilInventory.newBuilder()
                                                .addAllItems(utilItems)
                                                .build()
                                )
                )
                .build();
        FileHandle saveFile = Gdx.files.local("saves/save.swsave");
        saveFile.writeBytes(info.toByteArray(), false);
    }

    public static void load() throws InvalidProtocolBufferException {
        FileHandle saveFile = Gdx.files.local("saves/save.swsave");
        if (!saveFile.exists()) {
            return;
        }
        PlayerInfo info = PlayerInfo.parseFrom(saveFile.readBytes());
        me.partlysunny.shapewars.player.PlayerInfo customInfo = InGameScreen.playerInfo;
        PlayerEquipment customEquipment = customInfo.equipment();
        LevelManager levelManager = InGameScreen.levelManager;

        customInfo.setMaxHealth((int) info.getMaxHealth());
        customInfo.setHealth((int) info.getMaxHealth());
        customInfo.setMoney(info.getMoney());
        customInfo.inGameTime = info.getInGameTime();

        levelManager.setCurrentLevel(info.getLevel());

        for (PlayerInfo.AmmoManager.GunInfo gunInfo : info.getAmmoManager().getAmmoRemainingList()) {
            customInfo.ammoManager().setAmmo(gunInfo.getType(), gunInfo.getRemaining());
        }

        Item weapon1 = ItemManager.getItem(info.getEquipment().getWeapon1());
        if (weapon1 != null) {
            customEquipment.setWeaponOne((WeaponItem) weapon1);
        }
        Item weapon2 = ItemManager.getItem(info.getEquipment().getWeapon2());
        if (weapon2 != null) {
            customEquipment.setWeaponTwo((WeaponItem) weapon2);
        }
        Item armor1 = ItemManager.getItem(info.getEquipment().getArmor1());
        if (armor1 != null) {
            customEquipment.setArmorOne((ArmorItem) armor1);
        }
        Item armor2 = ItemManager.getItem(info.getEquipment().getArmor2());
        if (armor2 != null) {
            customEquipment.setArmorTwo((ArmorItem) armor2);
        }

        for (String s : info.getEquipment().getUnlockItems().getArmorsList()) {
            customEquipment.unlockArmor(s);
        }

        for (String s : info.getEquipment().getUnlockItems().getWeaponsList()) {
            customEquipment.unlockWeapon(s);
        }

        for (PlayerInfo.PlayerEquipment.UtilInventory.UtilItem entry : info.getEquipment().getUtilities().getItemsList()) {
            customEquipment.addUtilItems(entry.getType(), entry.getAmount());
        }
    }

    public static void deleteSave() {
        FileHandle saveFile = Gdx.files.local("saves/save.swsave");
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    public static boolean hasSave() {
        return Gdx.files.local("saves/save.swsave").exists();
    }
}
