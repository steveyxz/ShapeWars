package me.partlysunny.shapewars.item.items.armor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.item.types.ArmorItem;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class OldTunic implements ArmorItem {
    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Old Tunic";
    }

    @Override
    public String description() {
        return "Battered tunic, worn by a warrior many years ago.";
    }

    @Override
    public String texture() {
        return "oldTunic";
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
    public float getProtection() {
        return 0.1f;
    }

    @Override
    public void onHit(Entity attacker) {
    }
}
