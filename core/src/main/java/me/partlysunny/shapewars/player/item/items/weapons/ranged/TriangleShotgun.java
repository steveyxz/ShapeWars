package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class TriangleShotgun implements WeaponItem {
    @Override
    public int maxUses() {
        return 15;
    }

    @Override
    public float usesRegenRate() {
        return 3;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Triangle Shotgun";
    }

    @Override
    public String description() {
        return "Blasts your enemies, best at close range!";
    }

    @Override
    public String texture() {
        return "triangleShotgun";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 600;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 20;
    }

    @Override
    public float attackDelay() {
        return 1;
    }

    @Override
    public void attack(Entity attacker) {
        Controllers.SHOTGUN.fire(attacker, damage());
    }
}
