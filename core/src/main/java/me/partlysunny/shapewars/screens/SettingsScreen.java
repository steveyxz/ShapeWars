package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.MusicManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.Util;

public class SettingsScreen extends BasicGuiScreen {

    private static final Label.LabelStyle labelStyle = new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.5f), Color.BLACK);

    public SettingsScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void preGui() {
        camera.viewportWidth = camera.viewportWidth / 3f;
        camera.viewportHeight = camera.viewportHeight / 3f;
        camera.update();
        viewport.update((int) camera.viewportWidth, (int) camera.viewportHeight);
        viewport.apply();
    }

    @Override
    protected void createGui() {
        SettingsScreen s = this;
        Util.loadVisUI();

        VisTable table = new VisTable();
        table.setPosition(camera.viewportWidth / 2f, camera.viewportHeight / 2f);

        CheckBox music = new CheckBox("Music", VisUI.getSkin());
        music.setChecked(ShapeWars.settings.music());
        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ShapeWars.settings.setMusic(music.isChecked());
            }
        });
        Slider musicVolume = new Slider(0, 2, 0.1f, false, VisUI.getSkin());
        musicVolume.setValue(ShapeWars.settings.musicVolume());
        musicVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ShapeWars.settings.setMusicVolume(musicVolume.getValue());
            }
        });
        CheckBox sound = new CheckBox("Sound", VisUI.getSkin());
        sound.setChecked(ShapeWars.settings.sound());
        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ShapeWars.settings.setSound(sound.isChecked());
            }
        });
        Slider soundVolume = new Slider(0, 2, 0.1f, false, VisUI.getSkin());
        soundVolume.setValue(ShapeWars.settings.soundVolume());
        soundVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ShapeWars.settings.setSoundVolume(soundVolume.getValue());
            }
        });
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
    public void show() {
        super.show();
        MusicManager.stop(2);
    }
}
