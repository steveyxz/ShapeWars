package me.partlysunny.shapewars.util.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontPresets {

    public static BitmapFont RALEWAY_BOLD;
    public static BitmapFont RALEWAY_MEDIUM;
    public static BitmapFont RALEWAY_LIGHT;

    static {
        RALEWAY_BOLD = loadFont("Raleway-Bold.ttf", 40);
        RALEWAY_MEDIUM = loadFont("Raleway-Medium.ttf", 40);
        RALEWAY_LIGHT = loadFont("Raleway-ExtraLight.ttf", 40);
    }

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

    public static BitmapFont getFontWithSize(BitmapFont font, float size) {
        font.getData().setScale(size, -size);
        return font;
    }

}
