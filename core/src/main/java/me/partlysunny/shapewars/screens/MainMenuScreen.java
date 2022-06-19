package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.TextureManager;

public class MainMenuScreen extends ManagedScreen {

    public static final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Viewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
    private final ShapeWars game;
    private final Stage stage;

    public MainMenuScreen(ShapeWars game) {
        this.game = game;
        this.stage = new Stage(viewport, game.batch());
    }

    @Override
    protected void create() {
        viewport.apply();
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

        table.setDebug(true);

        ImageTextButton playButton = new ImageTextButton("Play", VisUI.getSkin(), "default");
        //playButton.setPosition(0, 300, Alignment.CENTER.getAlignment());
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(t)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
                }
                return true;
            }
        });
        playButton.setTransform(true);
        playButton.setSize(400, 150);

        Container<ImageTextButton> playButtonContainer = new Container<>(playButton);
        playButtonContainer.align(Alignment.CENTER.getAlignment());

        ImageTextButton quitButton = new ImageTextButton("Quit", VisUI.getSkin(), "default");
        //quitButton.setPosition(0, 500, Alignment.CENTER.getAlignment());
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(t)) {
                    Gdx.app.exit();
                }
                return true;
            }
        });
        playButton.setTransform(true);
        quitButton.setSize(400, 150);
        Container<ImageTextButton> quitButtonContainer = new Container<>(playButton);
        quitButtonContainer.align(Alignment.CENTER.getAlignment());

        VisImage logo = new VisImage(TextureManager.getTexture("mainScreenLogo"));
        //logo.setPosition(0, 100, Alignment.CENTER.getAlignment());
        logo.setSize(400, 150);
        Container<ImageTextButton> logoContainer = new Container<>(playButton);
        logoContainer.align(Alignment.CENTER.getAlignment());

        table.add(logoContainer, playButtonContainer, quitButtonContainer);
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();
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

    @Override
    public Color getClearColor() {
        return GameInfo.BACKGROUND_COLOR;
    }
}
