package me.partlysunny.shapewars.player.item.items.utility;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.player.item.types.UtilityItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class BasicHpPot implements UtilityItem {
    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Basic HP Pot";
    }

    @Override
    public String description() {
        return "Heals 5 HP";
    }

    @Override
    public String texture() {
        return "basicHpPot";
    }

    @Override
    public float renderSizeX() {
        return 4;
    }

    @Override
    public float renderSizeY() {
        return 4;
    }

    @Override
    public void use(Entity player) {
        InGameScreen.playerInfo.damage(-5);
    }
}
