package me.partlysunny.shapewars.world.components.enemy.loot;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;

/**
 * This represents an object that drops loot,
 * NOT A LOOT ITEM!!!!
 */
public class LootComponent implements Component, Pool.Poolable {

    private CustomLootTable table;

    public void init(CustomLootTable t) {
        table = t;
    }

    public CustomLootTable table() {
        return table;
    }

    @Override
    public void reset() {
        table = null;
    }
}
