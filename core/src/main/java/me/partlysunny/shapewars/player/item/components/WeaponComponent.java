package me.partlysunny.shapewars.player.item.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;

public class WeaponComponent implements Component, Pool.Poolable {

    private float attackCooldown = 0;
    private WeaponItem weaponItem = null;
    private boolean isSwinging = false;

    public void init(WeaponItem weaponItem) {
        this.weaponItem = weaponItem;
        this.attackCooldown = weaponItem.attackDelay();
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
        return attackCooldown == 0 && InGameScreen.playerInfo.ammoManager().canUse(weaponItem.texture());
    }

    public float attackCooldown() {
        return attackCooldown;
    }

    public void resetAttackCooldown() {
        attackCooldown = weaponItem.attackDelay();
    }

    public boolean isSwinging() {
        return isSwinging;
    }

    public void setSwinging(boolean swinging) {
        isSwinging = swinging;
    }

    @Override
    public void reset() {
        weaponItem = null;
        attackCooldown = 0;
        isSwinging = false;
    }
}
