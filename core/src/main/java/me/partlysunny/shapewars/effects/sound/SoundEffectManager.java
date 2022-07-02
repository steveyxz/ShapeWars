package me.partlysunny.shapewars.effects.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

import java.util.HashMap;
import java.util.Map;

public class SoundEffectManager {

    private static final Map<String, Sound> sounds = new HashMap<>();

    static {
        //Enemy
        loadWavRegular("enemySpawn");
        loadWavRegular("enemyDie");
        loadWavRegular("enemyBlasterShoot");
        //Player
        loadWavRegular("playerShoot");
        loadWavRegular("bulletCollide");
        //General
        loadWavRegular("countdown");
        loadWavRegular("click");
        loadWavRegular("levelStart");
    }

    public static void registerSound(String id, Sound sound) {
        sounds.put(id, sound);
    }

    public static Sound getSound(String id) {
        return sounds.get(id);
    }

    public static void unregisterSound(String id) {
        sounds.remove(id);
    }

    private static void loadWavRegular(String name) {
        registerSound(name, Gdx.audio.newSound(Gdx.files.internal("assets/sounds/" + name + ".wav")));
    }

    public static void play(String effect, float volume) {
        getSound(effect).play(volume);
    }

    public static void play(String effect, float volume, float soundX, float soundY) {
        TransformComponent playerTransform = Mappers.transformMapper.get(InGameScreen.playerInfo.playerEntity());
        volume = Util.getVolumeOfSoundFromPos(playerTransform.position.x, playerTransform.position.y, soundX, soundY, volume);
        getSound(effect).play(volume);
    }

}
