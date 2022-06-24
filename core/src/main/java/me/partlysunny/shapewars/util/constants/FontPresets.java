package me.partlysunny.shapewars.util.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class FontPresets {

    public static final String RALEWAY_BOLD = "Raleway-Bold.ttf";
    public static final String RALEWAY_MEDIUM = "Raleway-Medium.ttf";
    public static final String RALEWAY_LIGHT = "Raleway-ExtraLight.ttf";
    private static final Map<String, Map<Float, BitmapFont>> sizeMap = new HashMap<>();

    private static BitmapFont loadFont(String fontPath, int size) {
        //Generate a font object for font
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.flip = true;

        //The following settings allow the font to scale smoothly
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));

        BitmapFont myFont = generator.generateFont(parameter);
        myFont.setUseIntegerPositions(false);
        return myFont;
    }

    public static BitmapFont getFontWithSize(String path, float size) {
        if (sizeMap.containsKey(path)) {
            if (sizeMap.get(path).containsKey(size)) {
                return sizeMap.get(path).get(size);
            }
        } else {
            sizeMap.put(path, new HashMap<>());
        }
        BitmapFont newFont = loadFont(path, 30);
        newFont.getData().setScale(size, -size);
        sizeMap.get(path).put(size, newFont);
        return newFont;
    }

}
