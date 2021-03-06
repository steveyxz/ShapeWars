package me.partlysunny.shapewars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.protobuf.InvalidProtocolBufferException;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.player.SettingsManager;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.proto.GameSaver;
import me.partlysunny.shapewars.util.constants.Screens;
import me.partlysunny.shapewars.util.constants.Transitions;
import me.partlysunny.shapewars.util.utilities.TextureManager;

public class ShapeWars extends ManagedGame<ManagedScreen, ScreenTransition> {

    public static final SettingsManager settings = new SettingsManager();
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    @Override
    public void create() {
        super.create();
        try {
            reload();
        } catch (InvalidProtocolBufferException e) {
            Gdx.app.log("ERROR", "Loading game save failed! Corrupted save?", e);
        }
        screenManager.pushScreen("intro", "blending");
    }

    public void reload() throws InvalidProtocolBufferException {
        settings.load();
        ItemManager.init();
        TextureManager.initTextures();
        ParticleEffectManager.init();
        VisualEffectManager.init();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        Screens.init(this);
        Transitions.init(this);
        GameSaver.load();
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
