package me.partlysunny.shapewars.world.components.enemy.loot.table;

public class TableEntryWrapper {

    private String entry;
    private int weight;

    public TableEntryWrapper() {
        this("", 0);
    }

    public TableEntryWrapper(String entry, int weight) {
        this.entry = entry;
        this.weight = weight;
    }

    public String entry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public int weight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
