package me.partlysunny.shapewars.util.utilities;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.Map;

public class TextureRegionDrawableCache {

    private static final Map<String, TextureRegionDrawable> drawables = new HashMap<>();

    public static TextureRegionDrawable get(String id) {
        if (drawables.containsKey(id)) {
            return drawables.get(id);
        }
        TextureRegionDrawable drawable = new TextureRegionDrawable(TextureManager.getTexture(id));
        drawables.put(id, drawable);
        return drawable;
    }

}
