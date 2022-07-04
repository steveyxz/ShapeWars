package me.partlysunny.shapewars.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.prefs.PreferenceChangeEvent;

public class SettingsManager {

    private boolean music = true;
    private float musicVolume = 1;
    private boolean sound = true;
    private float soundVolume = 1;

    public boolean music() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
        save();
    }

    public float musicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
        save();
    }

    public boolean sound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
        save();
    }

    public float soundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
        save();
    }

    public void load() {
        Preferences settings = Gdx.app.getPreferences("userSettings");
        if (!settings.contains("music")) {
            save();
            return;
        }
        music = (settings.getBoolean("music"));
        musicVolume = (settings.getFloat("musicVolume"));
        sound = (settings.getBoolean("sound"));
        soundVolume = (settings.getFloat("soundVolume"));
    }

    public void save() {
        Preferences settings = Gdx.app.getPreferences("userSettings");
        settings.putBoolean("music", music);
        settings.flush();
        settings.putFloat("musicVolume", musicVolume);
        settings.flush();
        settings.putBoolean("sound", sound);
        settings.flush();
        settings.putFloat("soundVolume", soundVolume);
        settings.flush();
    }
}
