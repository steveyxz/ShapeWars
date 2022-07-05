package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.kotcrab.vis.ui.widget.VisImage;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.Util;

public class IntroScreen extends BasicGuiScreen {

    private float loadingTime = 4f;


    public IntroScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void additionalActs(float delta) {
        if (loadingTime > 0f) {
            loadingTime -= delta;
            if (loadingTime < 0f) {
                //Fade into main menu
                game.getScreenManager().pushScreen("mainMenu", "blending");
            }
        }
    }

    @Override
    protected void createGui() {
        Util.loadVisUI();

        VisImage logo = new VisImage(TextureManager.getTexture("logo"));
        Container<VisImage> logoContainer = new Container<>(logo);
        logoContainer.setSize(512, 512);
        logoContainer.setPosition(camera.viewportWidth / 2f - logoContainer.getWidth() / 2f, camera.viewportWidth / 2f - logoContainer.getHeight() / 2f);

        stage.addActor(logoContainer);
    }
}