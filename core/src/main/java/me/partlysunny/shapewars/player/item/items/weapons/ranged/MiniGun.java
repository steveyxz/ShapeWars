package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class MiniGun implements WeaponItem {
    @Override
    public int maxUses() {
        return 300;
    }

    @Override
    public float usesRegenRate() {
        return 0.1f;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Mini Gun";
    }

    @Override
    public String description() {
        return "BRrrHRRHHRHhrrh";
    }

    @Override
    public String texture() {
        return "miniGun";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 500;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 1;
    }

    @Override
    public float attackDelay() {
        return 0.05f;
    }

    @Override
    public void attack(Entity attacker) {
        Controllers.SNIPER.fire(attacker, damage());
    }
}
