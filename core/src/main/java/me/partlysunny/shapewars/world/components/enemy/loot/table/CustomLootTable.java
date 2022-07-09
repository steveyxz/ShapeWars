package me.partlysunny.shapewars.world.components.enemy.loot.table;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.partlysunny.shapewars.util.classes.RandomList;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.LootEntryManager;

import java.util.ArrayList;
import java.util.List;

public class CustomLootTable {

    private final RandomList<TableEntryWrapper> entries;
    private final Vector2 vec = new Vector2();
    private int rolls;

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
        if (toRemove != null) {
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
        for (int i = 0; i < rolls; i++) {
            TableEntryWrapper raffle = entries.raffle();
            if (raffle.entry() == null) {
                continue;
            }
            Vector3 position = Mappers.transformMapper.get(e).position;
            raffle.entry().execute(vec.set(position.x, position.y));
        }
    }

    public static final class LootTableBuilder {

        public static LootTableBuilder builder() {
            return new LootTableBuilder();
        }

        private int rolls = 1;
        private final List<TableEntryWrapper> entries;

        public LootTableBuilder() {
            this.entries = new ArrayList<>();
        }

        public LootTableBuilder addEntry(TableEntryWrapper entry) {
            entries.add(entry);
            return this;
        }

        public LootTableBuilder setRolls(int rolls) {
            this.rolls = rolls;
            return this;
        }

        public CustomLootTable build() {
            return new CustomLootTable(rolls, entries);
        }

    }
}
