package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.screens.*;

public final class Screens {

    public static void init(ShapeWars game) {
        game.getScreenManager().addScreen("intro", new IntroScreen(game));
        game.getScreenManager().addScreen("ingame", new InGameScreen(game));
        game.getScreenManager().addScreen("mainMenu", new MainMenuScreen(game));
        game.getScreenManager().addScreen("paused", new PauseScreen(game));
        game.getScreenManager().addScreen("death", new DeathScreen(game));
        game.getScreenManager().addScreen("settings", new SettingsScreen(game));
        game.getScreenManager().addScreen("loadOldSave", new LoadOldSaveScreen(game));
        game.getScreenManager().addScreen("end", new EndScreen(game));
    }

}
