package me.partlysunny.shapewars.player.equipment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.utilities.TextureRegionDrawableCache;

public class AmmoBar extends Actor {

    private final Drawable loadingBarBackground;
    private final Drawable loadingBar;
    private final int slot;

    public AmmoBar(int slot) {
        this.slot = slot;
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
