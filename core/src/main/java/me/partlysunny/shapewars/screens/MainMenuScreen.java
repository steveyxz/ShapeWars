package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.VisImage;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.MusicManager;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.proto.GameSaver;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

public class MainMenuScreen extends BasicGuiScreen {

    public MainMenuScreen(ShapeWars game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        MusicManager.play("shapeWarsTheme", true, 0.5f);
        Util.loadVisUI();
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
    protected void createGui() {
        MainMenuScreen t = this;
        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(TextureRegionDrawableCache.get("mainMenuButton"), TextureRegionDrawableCache.get("mainMenuButtonDown"), TextureRegionDrawableCache.get("mainMenuButtonChecked"), FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 3));
        buttonStyle.over = TextureRegionDrawableCache.get("mainMenuButtonChecked");

        ImageTextButton playButton = new ImageTextButton("Play", buttonStyle);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(t)) {
                    if (!GameSaver.hasSave()) {
                        game.getScreenManager().pushScreen("ingame", "blending");
                    } else {
                        game.getScreenManager().pushScreen("loadOldSave", "blending");
                    }
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

        ImageButton.ImageButtonStyle settingsStyle = new ImageButton.ImageButtonStyle();
        settingsStyle.up = TextureRegionDrawableCache.get("settings");
        settingsStyle.down = TextureRegionDrawableCache.get("settingsDown");
        settingsStyle.over = TextureRegionDrawableCache.get("settingsDown");

        ImageButton settings = new ImageButton(settingsStyle);
        settings.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(t)) {
                    game.getScreenManager().pushScreen("settings", "blending");
                }
                SoundEffectManager.play("click", 1);
                return false;
            }
        });

        Container<ImageButton> settingsContainer = new Container<>(settings);
        settingsContainer.setPosition(camera.viewportWidth * 4 / 5f, camera.viewportHeight / 2f);

        VisImage logo = new VisImage(TextureManager.getTexture("mainScreenLogo"));
        logo.setSize(800, 300);
        logo.setPosition(-1000, camera.viewportHeight * 3 / 4f - logo.getHeight() / 3f);

        playButtonContainer.setColor(1, 1, 1, 0);
        quitButtonContainer.setColor(1, 1, 1, 0);
        settingsContainer.setColor(1, 1, 1, 0);

        playButtonContainer.addAction(Actions.alpha(1, 1.5f, Interpolation.pow2In));
        quitButtonContainer.addAction(Actions.alpha(1, 1.5f, Interpolation.pow2In));
        settingsContainer.addAction(Actions.alpha(1, 3f, Interpolation.pow2In));
        logo.addAction(Actions.moveTo(camera.viewportWidth / 2f - logo.getWidth() / 2f, camera.viewportHeight * 3 / 4f - logo.getHeight() / 3f, 2f, Interpolation.pow2In));

        stage.addActor(logo);
        stage.addActor(playButtonContainer);
        stage.addActor(settingsContainer);
        stage.addActor(quitButtonContainer);
    }
}
