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

public class LevelManager {

    private final Stage guiStage;
    private final LevelSpawner spawner = new LevelSpawner(this);
    private final List<Entity> aliveEntities = new ArrayList<>();
    private final List<Level> levels = new ArrayList<>();
    private int currentLevel = 0;
    private float timeRemaining = 0;
    private boolean isLosing = false;

    public LevelManager(Stage guiStage) {
        loadLevels();
        this.guiStage = guiStage;
        initGui();
        checkLevelUp();
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
        InGameScreenGuiManager.registerGui("enemies", enemies, actor -> {
            ((Container<VisLabel>) actor).getActor().setText("Enemies Remaining: " + enemiesRemaining());
        });
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
            Map<String, Integer> enemies = new HashMap<>();
            Map<String, Integer> obstacles = new HashMap<>();
            JsonValue levelEnemies = value.get("enemies");
            for (JsonValue contentSection : levelEnemies) {
                String enemyType = contentSection.name;
                int enemyAmount = levelEnemies.getInt(contentSection.name);
                enemies.put(enemyType, enemyAmount);
            }
            JsonValue levelObstacles = value.get("obstacles");
            for (JsonValue contentSection : levelObstacles) {
                String obstacleType = contentSection.name;
                int obstacleAmount = levelObstacles.getInt(contentSection.name);
                obstacles.put(obstacleType, obstacleAmount);
            }
            this.levels.add(new Level(time, levelWidth, levelHeight, enemies, obstacles));
        }
    }

    public void entityDestroyed(Entity e) {
        if (aliveEntities.remove(e)) {
            if (!isLosing) {
                checkLevelUp();
            }
        }
    }

    public void addEnemy(Entity e) {
        aliveEntities.add(e);
    }

    private void checkLevelUp() {
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
        aliveEntities.clear();
    }

    public int enemiesRemaining() {
        return aliveEntities.size();
    }

    public void update(float delta) {
        timeRemaining -= delta;
        if (timeRemaining < 0) {
            lossReset();
        }
    }

    public void lossReset() {
        isLosing = true;
        killAllEnemies();
        if (currentLevel != 0) {
            currentLevel--;
        }
        checkLevelUp();
    }

}
