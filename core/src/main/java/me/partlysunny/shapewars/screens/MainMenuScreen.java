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
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.Util;

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
        Util.loadVisUI();
        Gdx.input.setInputProcessor(stage);

        VerticalGroup group = new VerticalGroup();
        group.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(new TextureRegionDrawable(TextureManager.getTexture("mainMenuButton")), new TextureRegionDrawable(TextureManager.getTexture("mainMenuButtonDown")), new TextureRegionDrawable(TextureManager.getTexture("mainMenuButtonChecked")), FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 3));

        group.setDebug(true);

        ImageTextButton playButton = new ImageTextButton("Play", buttonStyle);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(t)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
                }
                SoundEffectManager.play("click", 1);
                return false;
            }
        });
        Container<ImageTextButton> playButtonContainer = addProperties(playButton);

        ImageTextButton quitButton = new ImageTextButton("Quit", buttonStyle);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(t)) {
                    Gdx.app.exit();
                }
                SoundEffectManager.play("click", 1);
                return false;
            }
        });
        Container<ImageTextButton> quitButtonContainer = addProperties(quitButton);

        VisImage logo = new VisImage(TextureManager.getTexture("mainScreenLogo"));
        logo.setSize(400, 150);
        Container<VisImage> logoContainer = new Container<>(logo);
        logoContainer.align(Alignment.CENTER.getAlignment());

        group.addActor(logoContainer);
        group.addActor(playButton);
        group.addActor(quitButton);
        stage.addActor(group);
    }

    private Container<ImageTextButton> addProperties(ImageTextButton button) {
        button.setTransform(true);
        button.setOrigin(Alignment.CENTER.getAlignment());
        button.setScale(0.3f);

        Container<ImageTextButton> container = new Container<>(button);
        container.setTransform(true);
        return container;
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
