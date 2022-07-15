package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class SquareRifle implements WeaponItem {
    @Override
    public int maxUses() {
        return 5;
    }

    @Override
    public float usesRegenRate() {
        return 5;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Square Rifle";
    }

    @Override
    public String description() {
        return "Rifle or sniper? You choose.";
    }

    @Override
    public String texture() {
        return "squareRifle";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 120;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 80;
    }

    @Override
    public float attackDelay() {
        return 2;
    }

    @Override
    public void attack(Entity attacker) {
        Controllers.SNIPER.fire(attacker, damage());
    }
}
