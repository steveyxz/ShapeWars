package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import me.partlysunny.shapewars.player.InventoryMenuManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.player.item.ItemComponent;
import me.partlysunny.shapewars.world.components.player.item.WeaponComponent;

public class PlayerAttackingSystem extends IteratingSystem {


    public PlayerAttackingSystem() {
        super(Family.all(WeaponComponent.class, ItemComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WeaponComponent weapon = Mappers.weaponMapper.get(entity);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !InventoryMenuManager.isOpen()) {
            if (weapon.canAttack()) {
                weapon.weaponItem().attack(InGameScreen.playerInfo.playerEntity());
                weapon.resetAttackCooldown();
                InGameScreen.playerInfo.ammoManager().useAmmo(weapon.weaponItem().texture());
            }
        }
        weapon.update(deltaTime);
    }
}
