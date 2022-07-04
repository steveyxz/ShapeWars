package me.partlysunny.shapewars.item.items.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class WoodenStick implements WeaponItem {
    @Override
    public int maxUses() {
        return -1;
    }

    @Override
    public float usesRegenRate() {
        return 0;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Wooden Stick";
    }

    @Override
    public String description() {
        return "Just an ordinary stick.";
    }

    @Override
    public String texture() {
        return "woodenStick";
    }

    @Override
    public float renderSizeX() {
        return 1;
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
        return 0.8f;
    }

    @Override
    public void attack(Entity attacker) {

    }
}
