package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.widget.VisTable;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.MusicManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.Util;

public class EndScreen extends BasicGuiScreen {

    public EndScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void createGui() {
        EndScreen s = this;

        Util.loadVisUI();

        VisTable table = new VisTable();
        table.setPosition(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        stage.addActor(table);

        Label text = new Label("Victory!", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1.5f), Color.BLACK));
        Label text2 = new Label("Press enter to go back to the main menu", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1f), Color.BLACK));
        Label text3 = new Label("Your final time was: " + (Math.round(InGameScreen.playerInfo.inGameTime)) + "s", new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 1f), Color.BLACK));
        table.add(text);
        table.row().padTop(80);
        table.add(text2);
        table.row().padTop(80);
        table.add(text3);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if ((keycode == Input.Keys.ENTER) && game.getScreenManager().getCurrentScreen().equals(s)) {
                    ((InGameScreen) game.getScreenManager().getScreen("ingame")).delete();
                    game.reload();
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
