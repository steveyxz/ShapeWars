package me.partlysunny.shapewars.effects.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private static final Map<String, Music> tracks = new HashMap<>();
    private static String currentlyPlaying = "";

    public static void registerTrack(String id, Music track) {
        tracks.put(id, track);
    }

    public static Music getTrack(String id) {
        return tracks.get(id);
    }

    public static void unregisterTrack(String id) {
        tracks.remove(id);
    }

    public static void play(String track) {
        play(track, true, 1.0f);
    }

    public static void play(String track, boolean loop) {
        play(track, loop, 1.0f);
    }

    public static void play(String track, boolean loop, float volume) {
        if (!currentlyPlaying.equals("")) {
            getTrack(currentlyPlaying).stop();
        }
        getTrack(track).setVolume(volume);
        getTrack(track).setLooping(loop);
        getTrack(track).play();
        currentlyPlaying = track;
    }


    private static void loadMp3Regular(String name) {
        registerTrack(name, Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/" + name + ".mp3")));
    }

    static {
        loadMp3Regular("shapeWarsTheme");
    }

}
