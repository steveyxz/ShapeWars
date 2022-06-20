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
        registerTexture("player", new Texture(Gdx.files.internal("player.png")));
        registerTexture("logo", new Texture(Gdx.files.internal("logo.png")));
        registerTexture("wall", new Texture(Gdx.files.internal("wall.png")));
        registerTexture("rock", new Texture(Gdx.files.internal("rock.png")));
        registerTexture("mainScreenLogo", new Texture(Gdx.files.internal("mainScreenLogo.png")));
        registerTexture("circleBlaster", new Texture(Gdx.files.internal("circleBlaster.png")));
        registerTexture("circlePummeler", new Texture(Gdx.files.internal("circlePummeler.png")));
        registerTexture("basicBullet", new Texture(Gdx.files.internal("basicBullet.png")));
        registerTexture("enemy", new Texture(Gdx.files.internal("enemy.png")));
    }

}
