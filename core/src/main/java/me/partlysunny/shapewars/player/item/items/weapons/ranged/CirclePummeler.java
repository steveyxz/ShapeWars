package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class CirclePummeler implements WeaponItem {
    @Override
    public int maxUses() {
        return 20;
    }

    @Override
    public float usesRegenRate() {
        return 0.7f;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Circle Pummeler";
    }

    @Override
    public String description() {
        return "Pummels your enemies with circles";
    }

    @Override
    public String texture() {
        return "circlePummeler";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 20;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 8;
    }

    @Override
    public float attackDelay() {
        return 0.2f;
    }

    @Override
    public void attack(Entity attacker) {
        Controllers.BASIC.fire(attacker, damage());
    }
}
