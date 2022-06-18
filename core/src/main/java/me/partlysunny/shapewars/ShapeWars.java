package me.partlysunny.shapewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.partlysunny.shapewars.screens.Screens;

public class ShapeWars extends Game {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    @Override
    public void create() {
        TextureManager.initTextures();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        Screens.init(this);
        setScreen(Screens.inGameScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    public SpriteBatch batch() {
        return batch;
    }

    public ShapeRenderer shapeRenderer() {
        return shapeRenderer;
    }

    public BitmapFont font() {
        return font;
    }
}
