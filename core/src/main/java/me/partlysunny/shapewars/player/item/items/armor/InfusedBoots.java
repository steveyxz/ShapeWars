package me.partlysunny.shapewars.player.item.items.armor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.ArmorItem;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class InfusedBoots implements ArmorItem {
    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Infused Boots";
    }

    @Override
    public String description() {
        return "Magical boots, gives you loads of HP!";
    }

    @Override
    public String texture() {
        return "infusedBoots";
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
    public float getProtection() {
        return 0.1f;
    }

    @Override
    public int getBonusHealth() {
        return 180;
    }

    @Override
    public void onHit(Entity attacker) {
        //Nothing here
    }
}
