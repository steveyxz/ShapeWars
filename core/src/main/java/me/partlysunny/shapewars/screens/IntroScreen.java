package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.Util;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

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