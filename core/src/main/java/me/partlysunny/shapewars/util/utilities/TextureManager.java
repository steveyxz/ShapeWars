package me.partlysunny.shapewars.util.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    private static final Map<String, Texture> textures = new HashMap<>();

    public static void registerTexture(String id, Texture t) {
        textures.put(id, t);
    }

    public static void unregisterTexture(String id) {
        textures.remove(id);
    }

    public static Texture getTexture(String id) {
        return textures.get(id);
    }

    public static void initTextures() {
        //Logos
        doPngTexture("logo");
        doPngTexture("mainScreenLogo");
        //Entities
        doPngTexture("player");
        doPngTexture("wall");
        doPngTexture("rock");
        //UI
        doPngTexture("slotBackground");
        doPngTexture("slotBackgroundSelected");
        //Weapons
        doPngTexture("noWeapon");
        doPngTexture("circleBlaster");
        doPngTexture("circlePummeler");
        //Bullets
        doPngTexture("basicBullet");
        //Enemies
        doPngTexture("basic");
    }

    private static void doPngTexture(String value) {
        registerTexture(value, new Texture(Gdx.files.internal(value + ".png")));
    }

}
