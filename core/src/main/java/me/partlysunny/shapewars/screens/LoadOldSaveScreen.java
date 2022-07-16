package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.google.protobuf.InvalidProtocolBufferException;
import com.kotcrab.vis.ui.widget.VisTable;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.proto.GameSaver;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;
import me.partlysunny.shapewars.util.utilities.Util;

public class LoadOldSaveScreen extends BasicGuiScreen {

    public LoadOldSaveScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void createGui() {
        LoadOldSaveScreen s = this;

        Util.loadVisUI();

        VisTable table = new VisTable();
        table.setPosition(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        stage.addActor(table);

        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(TextureRegionDrawableCache.get("mainMenuButton"), TextureRegionDrawableCache.get("mainMenuButtonDown"), TextureRegionDrawableCache.get("mainMenuButtonChecked"), FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1));
        buttonStyle.over = TextureRegionDrawableCache.get("mainMenuButtonChecked");
        ImageTextButton text = new ImageTextButton("Load Save", buttonStyle);
        ImageTextButton text2 = new ImageTextButton("Delete Save", buttonStyle);
        table.add(text).size(400, 200);
        table.row().padTop(80);
        table.add(text2).size(400, 200);

        text.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.getScreenManager().getCurrentScreen().equals(s)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
                }
                SoundEffectManager.play("click", 1);
                return true;
            }
        });

        text2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((InGameScreen) game.getScreenManager().getScreen("ingame")).delete();
                GameSaver.deleteSave();
                try {
                    game.reload();
                } catch (InvalidProtocolBufferException e) {
                    Gdx.app.log("ERROR", "Loading game save failed! Corrupted save?", e);
                }
                if (game.getScreenManager().getCurrentScreen().equals(s)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
                }
                SoundEffectManager.play("click", 1);
                return true;
            }
        });
    }
}
