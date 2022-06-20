package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.ShapeWars;

public class PauseScreen extends ManagedScreen {

    public static final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static final Viewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
    private final ShapeWars game;
    private final Stage stage;


    public PauseScreen(ShapeWars game) {
        this.game = game;
        this.stage = new Stage(viewport, game.batch());
    }

    @Override
    protected void create() {
    }

    @Override
    public void show() {
        PauseScreen s = this;
        Gdx.input.setInputProcessor(stage);

        if (!VisUI.isLoaded()) {
            VisUI.load(new Skin(Gdx.files.internal("flatEarth/flat-earth-ui.json")));
        }

        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        VisLabel text = new VisLabel("Paused", Color.BLACK);
        table.add(text);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE && game.getScreenManager().getCurrentScreen().equals(s)) {
                    game.getScreenManager().pushScreen("ingame", "blending");
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
