package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.player.SettingsManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.Util;

public class SettingsScreen extends ManagedScreen {

    public static final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);
    public static final Viewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
    private final ShapeWars game;
    private final Stage stage;
    private final Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.5f), Color.BLACK);


    public SettingsScreen(ShapeWars game) {
        this.game = game;
        this.stage = new Stage(viewport, game.batch());

        Util.loadVisUI();

        VisTable table = new VisTable();
        table.setFillParent(true);

        CheckBox music = new CheckBox("Music", VisUI.getSkin());
        music.setChecked(ShapeWars.settings.music());
        Slider musicVolume = new Slider(0, 2, 0.1f, false, VisUI.getSkin());
        musicVolume.setValue(ShapeWars.settings.musicVolume());
        CheckBox sound = new CheckBox("Sound", VisUI.getSkin());
        sound.setChecked(ShapeWars.settings.sound());
        Slider soundVolume = new Slider(0, 2, 0.1f, false, VisUI.getSkin());
        soundVolume.setValue(ShapeWars.settings.soundVolume());
        Label prompt = new Label("Press ESC to go back!", labelStyle);

        table.add(music);
        table.row().padBottom(4);
        table.add(musicVolume);
        table.row().padBottom(4);
        table.add(sound);
        table.row().padBottom(4);
        table.add(soundVolume);
        table.row().padTop(40);
        table.add(prompt).padTop(40);

        stage.addActor(table);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ShapeWars.settings.setSound(sound.isChecked());
                ShapeWars.settings.setMusic(music.isChecked());
                ShapeWars.settings.setSoundVolume(soundVolume.getValue());
                ShapeWars.settings.setMusicVolume(musicVolume.getValue());
                ShapeWars.settings.save();
            }
        }, 0, 0.2f);

    }

    @Override
    protected void create() {
    }

    @Override
    public void show() {
        SettingsScreen s = this;
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE && game.getScreenManager().getCurrentScreen().equals(s)) {
                    game.getScreenManager().pushScreen("mainMenu", "blending");
                }
                return true;
            }
        });
    }

    @Override
    public void hide() {

    }

    public void render(float delta) {
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }

    @Override
    public Color getClearColor() {
        return GameInfo.BACKGROUND_COLOR;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
