package me.partlysunny.shapewars.effects.sound;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class EffectManager {

    private static final Map<String, Sound> sounds = new HashMap<>();

    public static void registerSound(String id, Sound sound) {
        sounds.put(id, sound);
    }

    public static Sound getSound(String id) {
        return sounds.get(id);
    }

    public static void unregisterSound(String id) {
        sounds.remove(id);
    }

    private static void loadMp3Regular(String name) {
        registerSound(name, Gdx.audio.newSound(Gdx.files.internal("assets/sounds/" + name + ".mp3")));
    }

    static {
    }

}
