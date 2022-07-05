package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.Util;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.*;

public class DeathScreen extends BasicGuiScreen {

    private Container<Label> youDiedLabel;
    private Container<Label> respawnLabel;


    public DeathScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void additionalActs(float delta) {
        youDiedLabel.setTransform(true);
        youDiedLabel.setPosition(metersToPixels(FRUSTUM_WIDTH) / 2f, metersToPixels(FRUSTUM_HEIGHT) * 7 / 12f);
        respawnLabel.setTransform(true);
        respawnLabel.setPosition(metersToPixels(FRUSTUM_WIDTH) / 2f, metersToPixels(FRUSTUM_HEIGHT) * 5 / 12f);
    }

    @Override
    protected void createGui() {
        DeathScreen s = this;

        Util.loadVisUI();

        Label youDied = new Label("You Died!", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1.5f), Color.BLACK));
        Label respawn = new Label("Press enter to respawn.", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1), Color.BLACK));

        youDiedLabel = new Container<>(youDied);
        respawnLabel = new Container<>(respawn);

        stage.addActor(youDiedLabel);
        stage.addActor(respawnLabel);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER && game.getScreenManager().getCurrentScreen().equals(s)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
                }
                return true;
            }
        });
    }

}
