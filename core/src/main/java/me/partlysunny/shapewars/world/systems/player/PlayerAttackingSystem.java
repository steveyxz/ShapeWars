package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.components.player.equipment.item.components.ItemComponent;
import me.partlysunny.shapewars.world.components.player.equipment.item.components.WeaponComponent;

public class PlayerAttackingSystem extends IteratingSystem {

    private ComponentMapper<WeaponComponent> weaponMapper = ComponentMapper.getFor(WeaponComponent.class);

    public PlayerAttackingSystem() {
        super(Family.all(WeaponComponent.class, ItemComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WeaponComponent weapon = weaponMapper.get(entity);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (weapon.canAttack()) {
                weapon.weaponItem().attack(InGameScreen.playerInfo.playerEntity());
                weapon.resetAttackCooldown();
            }
        }
        weapon.update(deltaTime);
    }
}
