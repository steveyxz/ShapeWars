package me.partlysunny.shapewars.player.item.items.armor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.ArmorItem;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class PaddedGreaves implements ArmorItem {
    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Padded Greaves";
    }

    @Override
    public String description() {
        return "Worn by a noble knight, will serve you well";
    }

    @Override
    public String texture() {
        return "paddedGreaves";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 80;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public float getProtection() {
        return 0.25f;
    }

    @Override
    public int getBonusHealth() {
        return 50;
    }

    @Override
    public void onHit(Entity attacker) {
        //Nothing happens
    }
}
