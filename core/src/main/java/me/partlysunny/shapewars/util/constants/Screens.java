package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.screens.IntroScreen;
import me.partlysunny.shapewars.screens.MainMenuScreen;
import me.partlysunny.shapewars.screens.PauseScreen;

public class Screens {

    public static IntroScreen introScreen;
    public static InGameScreen inGameScreen;
    public static MainMenuScreen mainMenuScreen;
    public static PauseScreen pausedScreen;

    public static void init(ShapeWars game) {
        introScreen = new IntroScreen(game);
        inGameScreen = new InGameScreen(game);
        mainMenuScreen = new MainMenuScreen(game);
        pausedScreen = new PauseScreen(game);
        game.getScreenManager().addScreen("intro", introScreen);
        game.getScreenManager().addScreen("ingame", inGameScreen);
        game.getScreenManager().addScreen("mainMenu", mainMenuScreen);
        game.getScreenManager().addScreen("paused", pausedScreen);
    }

}
