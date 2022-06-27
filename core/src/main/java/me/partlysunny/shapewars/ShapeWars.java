package me.partlysunny.shapewars;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.util.constants.Screens;
import me.partlysunny.shapewars.util.constants.Transitions;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.item.items.ItemManager;

public class ShapeWars extends ManagedGame<ManagedScreen, ScreenTransition> {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;


    @Override
    public void create() {
        super.create();
        ItemManager.init();
        TextureManager.initTextures();
        ParticleEffectManager.init();
        VisualEffectManager.init();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        Screens.init(this);
        Transitions.init(this);
        screenManager.pushScreen("mainMenu", "blending");
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
