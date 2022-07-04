package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.util.constants.GameInfo;

public abstract class BasicGuiScreen extends ManagedScreen {

    protected final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    protected final Viewport viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera);
    protected final ShapeWars game;
    protected final Stage stage;

    public BasicGuiScreen(ShapeWars game) {
        this.game = game;
        this.stage = new Stage(viewport, game.batch());
        preGui();
        createGui();
    }

    public ShapeWars game() {
        return game;
    }

    public Stage stage() {
        return stage;
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void create() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act(delta);
        additionalActs(delta);
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

    protected void additionalActs(float delta) {
    }

    protected void preGui() {
    }

    protected abstract void createGui();
}
