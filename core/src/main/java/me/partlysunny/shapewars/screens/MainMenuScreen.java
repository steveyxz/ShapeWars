package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.MusicManager;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class MainMenuScreen extends ManagedScreen {

    public static final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Viewport viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera);
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
        MusicManager.play("shapeWarsTheme", true, 0.5f);
        MainMenuScreen t = this;
        Util.loadVisUI();
        Gdx.input.setInputProcessor(stage);

        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(TextureRegionDrawableCache.get("mainMenuButton"), TextureRegionDrawableCache.get("mainMenuButtonDown"), TextureRegionDrawableCache.get("mainMenuButtonChecked"), FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 3));

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
        playButtonContainer.setPosition(camera.viewportHeight / 2f, camera.viewportHeight / 2f);

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
        quitButtonContainer.setPosition(camera.viewportWidth / 2f, camera.viewportHeight / 4f);

        VisImage logo = new VisImage(TextureManager.getTexture("mainScreenLogo"));
        logo.setSize(800, 300);
        logo.setPosition(-1000, camera.viewportHeight * 3 / 4f - logo.getHeight() / 3f);

        playButtonContainer.setColor(1, 1, 1, 0);
        quitButtonContainer.setColor(1, 1, 1, 0);

        playButtonContainer.addAction(Actions.alpha(1, 1.5f, Interpolation.pow2In));
        quitButtonContainer.addAction(Actions.alpha(1, 1.5f, Interpolation.pow2In));
        logo.addAction(Actions.moveTo(camera.viewportWidth / 2f - logo.getWidth() / 2f, camera.viewportHeight * 3 / 4f - logo.getHeight() / 3f, 2f, Interpolation.pow2In));

        stage.addActor(logo);
        stage.addActor(playButtonContainer);
        stage.addActor(quitButtonContainer);
    }

    private Container<ImageTextButton> addProperties(ImageTextButton button) {
        button.setTransform(true);
        button.setOrigin(Alignment.CENTER.getAlignment());
        button.setScale(0.4f);

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
