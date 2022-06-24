package me.partlysunny.shapewars.item.items.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.bullets.controllers.Controllers;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class CircleBlaster implements WeaponItem {
    @Override
    public int maxUses() {
        return 20;
    }

    @Override
    public float usesRegenRate() {
        return 1;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Circle Blaster";
    }

    @Override
    public String description() {
        return "Blasts circles";
    }

    @Override
    public String texture() {
        return "circleBlaster";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 5;
    }

    @Override
    public float attackDelay() {
        return 0.5f;
    }

    @Override
    public void attack(Entity attacker) {
        Controllers.BASIC.fire(attacker, damage());
    }
}
