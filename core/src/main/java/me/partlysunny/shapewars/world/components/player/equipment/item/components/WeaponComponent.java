package me.partlysunny.shapewars.world.components.player.equipment.item.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.world.components.player.equipment.item.types.WeaponItem;

public class WeaponComponent implements Component, Pool.Poolable {

    private float attackCooldown = 0;
    private WeaponItem weaponItem = null;

    public void init(WeaponItem weaponItem) {
        this.weaponItem = weaponItem;
    }

    public void update(float delta) {
        attackCooldown -= delta;
        if (attackCooldown < 0) {
            attackCooldown = 0;
        }
    }

    public WeaponItem weaponItem() {
        return weaponItem;
    }

    public boolean canAttack() {
        return attackCooldown == 0;
    }

    public float attackCooldown() {
        return attackCooldown;
    }

    public void resetAttackCooldown() {
        attackCooldown = weaponItem.attackDelay();
    }

    @Override
    public void reset() {
        weaponItem = null;
        attackCooldown = 0;
    }
}
