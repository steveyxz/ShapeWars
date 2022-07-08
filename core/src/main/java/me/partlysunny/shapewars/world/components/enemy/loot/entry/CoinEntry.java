package me.partlysunny.shapewars.world.components.enemy.loot.entry;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.utilities.Util;

public class CoinEntry implements Entry {

    private final int min;
    private final int max;
    private final int maxVisibleCoins;

    public CoinEntry(int min, int max, int maxVisibleCoins) {
        this.min = min;
        this.max = max;
        this.maxVisibleCoins = maxVisibleCoins;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    @Override
    public void execute(Vector2 pos) {
        int moneyToGive = Util.getRandomBetween(min, max);
        int base = moneyToGive / maxVisibleCoins;
        int extras = moneyToGive % max;

    }

    private void summonCoin(int value) {
        PooledEngine engine = InGameScreen.world.gameWorld();
        Entity coin = engine.createEntity();


    }
}
