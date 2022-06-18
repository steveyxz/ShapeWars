package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.TextureManager;

public class MainMenuScreen extends ScreenAdapter {

    public static final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Viewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
    private final ShapeWars game;
    private final Stage stage;

    public MainMenuScreen(ShapeWars game) {
        this.game = game;
        this.stage = new Stage(viewport, game.batch());
    }

    @Override
    public void show() {
        MainMenuScreen t = this;
        if (!VisUI.isLoaded()) {
            VisUI.load(new Skin(Gdx.files.internal("flatEarth/flat-earth-ui.json")));
        }
        Gdx.input.setInputProcessor(stage);

        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        //table.setDebug(true);

        ImageTextButton playButton = new ImageTextButton("Play", VisUI.getSkin(), "default");
        playButton.setPosition(0, 300, Alignment.CENTER.getAlignment());
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreen().equals(t)) {
                    game.setScreen(Screens.inGameScreen);
                }
                return true;
            }
        });
        playButton.setSize(400, 150);

        ImageTextButton quitButton = new ImageTextButton("Quit", VisUI.getSkin(), "default");
        quitButton.setPosition(0, 500, Alignment.CENTER.getAlignment());
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreen().equals(t)) {
                    Gdx.app.exit();
                }
                return true;
            }
        });
        quitButton.setSize(400, 150);

        VisImage logo = new VisImage(TextureManager.getTexture("mainScreenLogo"));
        logo.setPosition(0, 100, Alignment.CENTER.getAlignment());
        logo.setSize(400, 150);

        table.add(logo, playButton, quitButton);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(GameInfo.BACKGROUND_COLOR);
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
