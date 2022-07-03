package me.partlysunny.shapewars.player;

import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.screens.InGameScreen;

public class PlayerKiller {

    private static boolean isDying = false;

    public static void kill() {
        isDying = true;
    }

    public static void update(ShapeWars game) {
        if (isDying) {
            isDying = false;
            InGameScreen.levelManager.lossReset();
            InGameScreen.playerInfo.setHealth(InGameScreen.playerInfo.maxHealth());
            game.getScreenManager().pushScreen("death", null);
        }
    }

}
