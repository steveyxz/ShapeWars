package me.partlysunny.shapewars.world.components.mechanics.enemy.loot.table;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.partlysunny.shapewars.util.classes.RandomList;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.mechanics.enemy.loot.entry.LootEntryManager;

import java.util.ArrayList;
import java.util.List;

public class CustomLootTable {

    private final RandomList<TableEntryWrapper> entries;
    private int rolls;
    private final Vector2 vec = new Vector2();

    public CustomLootTable(int rolls, List<TableEntryWrapper> entries) {
        RandomList<TableEntryWrapper> l = new RandomList<>();
        for (TableEntryWrapper entry : entries) {
            l.add(entry, entry.weight());
        }
        this.entries = l;
        this.rolls = rolls;
    }

    public void removeEntry(TableEntryWrapper w) {
        RandomList<TableEntryWrapper>.RandomCollectionObject<TableEntryWrapper> toRemove = null;
        for (RandomList<TableEntryWrapper>.RandomCollectionObject<TableEntryWrapper> e : entries) {
            if (e.getObject().equals(w)) {
                toRemove = e;
            }
        }
        if (!(toRemove == null)) {
            entries.remove(toRemove);
        }
    }

    public RandomList<TableEntryWrapper> getEntries() {
        return entries;
    }

    public RandomList<TableEntryWrapper> entries() {
        return entries;
    }

    public int rolls() {
        return rolls;
    }

    public void setRolls(int rolls) {
        this.rolls = rolls;
    }

    public void dropTableAt(Entity e) {
        List<String> hasSaid = new ArrayList<>();
        for (int i = 0; i < rolls; i++) {
            TableEntryWrapper raffle = entries.raffle();
            if (raffle.entry() == null) {
                continue;
            }
            Vector3 position = Mappers.transformMapper.get(e).position;
            LootEntryManager.getEntry(raffle.entry()).execute(vec.set(position.x, position.y));
        }
    }
}
