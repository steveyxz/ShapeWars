package me.partlysunny.shapewars.world.components.enemy.loot.entry.coin;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CoinComponent implements Component, Pool.Poolable {

    private int amount = 0;

    public int amount() {
        return amount;
    }

    public void init(int amount) {
        this.amount = amount;
    }

    @Override
    public void reset() {
        amount = 0;
    }
}
