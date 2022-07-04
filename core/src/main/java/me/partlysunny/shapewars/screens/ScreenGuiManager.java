package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ScreenGuiManager {

    private final Map<String, Actor> guis = new HashMap<>();
    private final Map<String, Consumer<Actor>> update = new HashMap<>();
    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void registerGui(String id, Actor gui, @Nullable Consumer<Actor> action) {
        guis.put(id, gui);
        update.put(id, action);
        stage.addActor(gui);
    }

    public Actor getGui(String id) {
        return guis.get(id);
    }

    public void unregisterGui(String id) {
        guis.remove(id);
    }

    public void update() {
        for (String key : update.keySet()) {
            Consumer<Actor> action = update.get(key);
            Actor actor = guis.get(key);
            if (action != null && actor != null) {
                action.accept(actor);
            }
        }
    }

}
