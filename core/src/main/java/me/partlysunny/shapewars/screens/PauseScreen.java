package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.Util;

public class PauseScreen extends BasicGuiScreen {


    public PauseScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void createGui() {
        PauseScreen s = this;

        Util.loadVisUI();

        VisTable table = new VisTable();
        table.setPosition(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        stage.addActor(table);

        Label text = new Label("Paused", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1.5f), Color.BLACK));
        table.add(text);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if ((keycode == Input.Keys.SPACE || keycode == Input.Keys.ESCAPE) && game.getScreenManager().getCurrentScreen().equals(s)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
                }
                return true;
            }
        });
    }

}
