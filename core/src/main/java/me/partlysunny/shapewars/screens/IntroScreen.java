package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.TextureManager;

public class IntroScreen extends ScreenAdapter {

    private final ShapeWars game;
    private float loadingTime = 4f;
    public static final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Viewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
    private final Stage stage;


    public IntroScreen(ShapeWars game) {
        this.game = game;
        this.stage = new Stage(viewport, game.batch());
    }

    @Override
    public void show() {

        if (!VisUI.isLoaded()) {
            VisUI.load(new Skin(Gdx.files.internal("flatEarth/flat-earth-ui.json")));
        }
        Gdx.input.setInputProcessor(stage);

        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        VisImage logo = new VisImage(TextureManager.getTexture("logo"));

        table.add(logo);

    }

    public void render(float delta) {
        ScreenUtils.clear(GameInfo.BACKGROUND_COLOR);
        if (loadingTime > 0f) {
            loadingTime -= delta;
            if (loadingTime < 0f) {
                //Fade into main menu
                game.setScreen(Screens.mainMenuScreen);
            }
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}