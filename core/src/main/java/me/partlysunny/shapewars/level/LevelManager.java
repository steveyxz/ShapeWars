package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import me.partlysunny.shapewars.screens.InGameScreenGuiManager;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.world.objects.obstacle.WallEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class LevelManager {

    private final Stage guiStage;
    private int currentLevel = 0;
    private final EnemySpawner spawner = new EnemySpawner();
    private final List<Entity> aliveEntities = new ArrayList<>();
    private final List<Level> levels = new ArrayList<>();
    private float timeRemaining = 0;

    public LevelManager(Stage guiStage) {
        loadLevels();
        this.guiStage = guiStage;
        initGui();
        updateLevel();
    }

    private void initGui() {

        if (!VisUI.isLoaded()) {
            VisUI.load(new Skin(Gdx.files.internal("flatEarth/flat-earth-ui.json")));
        }

        VisLabel currentLevel = new VisLabel("Current Level: " + this.currentLevel, "font", Color.BLACK);
        VisLabel enemiesRemaining = new VisLabel("Enemies Remaining: " + enemiesRemaining(), "font", Color.BLACK);
        VisLabel timeLabel = new VisLabel("Time Remaining: " + timeRemaining, "font", Color.BLACK);

        Container<VisLabel> level = new Container<>(currentLevel), enemies = new Container<>(enemiesRemaining), time = new Container<>(timeLabel);
        level.setTransform(true);
        enemies.setTransform(true);
        time.setTransform(true);

        level.setPosition(20, FRUSTUM_HEIGHT - 5f);
        enemies.setPosition(20, FRUSTUM_HEIGHT - 10f);
        time.setPosition(20, FRUSTUM_HEIGHT - 15f);
        level.setScale(0.2f, 0.2f);
        enemies.setScale(0.2f, 0.2f);
        time.setScale(0.2f, 0.2f);

        InGameScreenGuiManager.registerGui("level", level, actor -> ((Container<VisLabel>) actor).getActor().setText("Current Level: " + this.currentLevel));
        InGameScreenGuiManager.registerGui("enemies", enemies, actor -> ((Container<VisLabel>) actor).getActor().setText("Enemies Remaining: " + enemiesRemaining()));
        InGameScreenGuiManager.registerGui("time", time, actor -> ((Container<VisLabel>) actor).getActor().setText("Time Remaining: " + ((int) (timeRemaining))));
    }

    private void loadLevels() {
        FileHandle levels = Gdx.files.internal("assets/levels");
        for (FileHandle file : levels.list()) {
            JsonReader reader = new JsonReader();
            JsonValue value = reader.parse(file.reader());
            int levelWidth = value.getInt("levelWidth");
            int levelHeight = value.getInt("levelHeight");
            int time = value.getInt("time");
            JsonValue levelContents = value.get("contents");
            Map<String, Integer> enemies = new HashMap<>();
            for (JsonValue contentSection : levelContents) {
                String enemyType = contentSection.name;
                int enemyAmount = levelContents.getInt(contentSection.name);
                enemies.put(enemyType, enemyAmount);
            }
            this.levels.add(new Level(time, levelWidth, levelHeight, enemies));
        }
    }

    public void entityDestroyed(Entity e) {
        if (aliveEntities.remove(e)) {
            updateLevel();
        }
    }

    private void updateLevel() {
        if (enemiesRemaining() == 0) {
            currentLevel++;
            Level newLevel = getCurrentLevel();
            spawner.spawn(newLevel);
            WallEntity.reloadWalls(newLevel.levelWidth(), newLevel.levelHeight());
            timeRemaining = newLevel.time();
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevel - 1);
    }

    public void killAllEnemies() {
        for (Entity e : aliveEntities) {
            LateRemover.tagToRemove(e);
        }
    }

    public int enemiesRemaining() {
        return aliveEntities.size();
    }

    public void update(float delta) {
        timeRemaining -= delta;
        if (timeRemaining < 0) {
            if (currentLevel != 1) {
                currentLevel--;
            }
            killAllEnemies();
            updateLevel();
        }
    }

}
