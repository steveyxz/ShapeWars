package me.partlysunny.shapewars.world.components.enemy.loot.table;

import me.partlysunny.shapewars.world.components.enemy.loot.entry.Entry;

public class TableEntryWrapper {

    private Entry entry;
    private int weight;

    public TableEntryWrapper() {
        this(null, 0);
    }

    public TableEntryWrapper(Entry entry, int weight) {
        this.entry = entry;
        this.weight = weight;
    }

    public Entry entry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public int weight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
