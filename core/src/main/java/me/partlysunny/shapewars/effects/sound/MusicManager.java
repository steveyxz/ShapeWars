package me.partlysunny.shapewars.effects.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;
import me.partlysunny.shapewars.ShapeWars;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private static final Map<String, Music> tracks = new HashMap<>();
    private static String currentlyPlaying = "";
    private static String nextTrack = "";
    private static float nextVolume = 0;
    private static boolean nextLooping = true;
    private static boolean stopping = false;

    static {
        loadMp3Regular("shapeWarsTheme");
        loadMp3Regular("squaresAndCircles");
    }

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
        if (ShapeWars.settings.music()) {
            if (stopping) {
                nextVolume = volume * ShapeWars.settings.musicVolume();
                nextTrack = track;
                nextLooping = loop;
            } else {
                if (!currentlyPlaying.equals("")) {
                    getTrack(currentlyPlaying).stop();
                }
                getTrack(track).setVolume(volume * ShapeWars.settings.musicVolume());
                getTrack(track).setLooping(loop);
                getTrack(track).play();
                currentlyPlaying = track;
            }
        }
    }

    public static void stop() {
        if (!currentlyPlaying.equals("")) {
            getTrack(currentlyPlaying).stop();
        }
    }

    public static void stop(float fadeOutIn) {
        if (!currentlyPlaying.equals("")) {
            stopping = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    float newVolume = getTrack(currentlyPlaying).getVolume() - (1 / 20f);
                    if (newVolume < 0) {
                        getTrack(currentlyPlaying).stop();
                        stopping = false;
                        if (!nextTrack.equals("")) {
                            play(nextTrack, nextLooping, nextVolume);
                            nextTrack = "";
                            nextLooping = true;
                            nextVolume = 0;
                        }
                        cancel();
                        return;
                    }
                    getTrack(currentlyPlaying).setVolume(newVolume);
                }
            }, 0, fadeOutIn / 20f);
        }
    }

    private static void loadMp3Regular(String name) {
        registerTrack(name, Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/" + name + ".mp3")));
    }
}
