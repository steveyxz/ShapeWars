package me.partlysunny.shapewars.world.components.player.equipment.item.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.world.components.player.equipment.item.Item;

public class ItemComponent implements Component, Pool.Poolable {

    private Entity player = null;
    private Item item = null;

    public void init(Entity player, Item item) {
        this.player = player;
        this.item = item;
    }

    public Entity player() {
        return player;
    }

    public Item item() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void reset() {
        player = null;
    }
}
