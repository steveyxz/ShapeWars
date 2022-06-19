package me.partlysunny.shapewars;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import me.partlysunny.shapewars.screens.Screens;
import me.partlysunny.shapewars.screens.Transitions;
import me.partlysunny.shapewars.world.components.player.equipment.item.ItemManager;

public class ShapeWars extends ManagedGame<ManagedScreen, ScreenTransition> {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;


    @Override
    public void create() {
        super.create();
        ItemManager.init();
        TextureManager.initTextures();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        Screens.init(this);
        Transitions.init(this);
        screenManager.pushScreen("intro", "blending");
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
