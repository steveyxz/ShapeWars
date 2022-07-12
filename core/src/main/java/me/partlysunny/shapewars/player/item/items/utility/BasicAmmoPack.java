package me.partlysunny.shapewars.player.item.items.utility;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.player.item.types.UtilityItem;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class BasicAmmoPack implements UtilityItem {

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Basic Ammo Pack";
    }

    @Override
    public String description() {
        return "Gives your current weapon +40% ammo!";
    }

    @Override
    public String texture() {
        return "basicAmmoPack";
    }

    @Override
    public float renderSizeX() {
        return 4;
    }

    @Override
    public int price() {
        return 25;
    }

    @Override
    public float renderSizeY() {
        return 4;
    }

    @Override
    public void use(Entity player) {
        WeaponItem weapon = InGameScreen.playerInfo.equipment().currentWeapon();
        if (weapon != null && weapon.maxUses() != -1) {
            InGameScreen.playerInfo.ammoManager().setAmmo(weapon.texture(), (int) (InGameScreen.playerInfo.ammoManager().ammoRemaining(weapon.texture()) + (weapon.maxUses() * 4 / 10)));
            SoundEffectManager.play("ammoReload", 1);
        } else {
            InGameScreen.playerInfo.equipment().addUtilItems("basicAmmoPack", 1);
        }
    }

}
