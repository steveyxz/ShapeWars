package me.partlysunny.shapewars.player.equipment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.VisUI;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;

public class AmmoBar extends Actor {

    private Drawable loadingBarBackground;
    private Drawable loadingBar;
    private int slot = 0;

    public AmmoBar(int slot) {
        this.slot = slot;
        TextureAtlas skinAtlas = VisUI.getSkin().getAtlas();
        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("slider-vertical"), 5, 5, 4, 4);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("slider-knob"), 5, 5, 4, 4);
        loadingBar = TextureRegionDrawableCache.get("progressBarKnob");
        loadingBarBackground = TextureRegionDrawableCache.get("progressBar");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        WeaponItem i = slot == 0 ? InGameScreen.playerInfo.equipment().weaponOne() : InGameScreen.playerInfo.equipment().weaponTwo();
        if (i == null || i.maxUses() == -1) {
            return;
        }
        float progress = InGameScreen.playerInfo.ammoManager().ammoRemaining(i.texture()) / (float) i.maxUses();

        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        loadingBar.draw(batch, getX(), getY(), getWidth() * getScaleX(), progress * getHeight() * getScaleY());
    }

}
