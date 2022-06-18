package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Screen;
import me.partlysunny.shapewars.ShapeWars;

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
    }

}
