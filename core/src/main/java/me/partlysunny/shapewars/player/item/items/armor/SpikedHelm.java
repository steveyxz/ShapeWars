package me.partlysunny.shapewars.player.item.items.armor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.ArmorItem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.ItemFactory;
import me.partlysunny.shapewars.util.utilities.Util;

public class SpikedHelm implements ArmorItem  {
    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Spiked Helm";
    }

    @Override
    public String description() {
        return "Damages enemies which hit you!";
    }

    @Override
    public String texture() {
        return "spikedHelm";
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
        return 0.15f;
    }

    @Override
    public int getBonusHealth() {
        return 20;
    }

    @Override
    public void onHit(Entity attacker) {
        if (Mappers.healthMapper.has(attacker)) {
            Util.playDamage(attacker, 3);
        }
    }
}
