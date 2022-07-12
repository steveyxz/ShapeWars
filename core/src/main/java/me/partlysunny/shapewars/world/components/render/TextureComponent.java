package me.partlysunny.shapewars.world.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable {

    private TextureRegion texture;
    private boolean isHidden = false;
    private int zIndex;

    public void init(TextureRegion texture) {
        this.texture = texture;
        zIndex = 0;
    }

    public void init(TextureRegion texture, int zIndex) {
        this.texture = texture;
        this.zIndex = zIndex;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public TextureRegion texture() {
        return texture;
    }

    @Override
    public void reset() {
        texture = null;
    }

    public int zIndex() {
        return zIndex;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}
