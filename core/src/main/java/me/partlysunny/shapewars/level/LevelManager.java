package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.VisUI;
import me.partlysunny.shapewars.screens.InGameScreenGuiManager;
import me.partlysunny.shapewars.util.constants.FontPresets;
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
    private final List<Entity> aliveObstacles = new ArrayList<>();
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

        Label currentLevel = new Label("Current Level: " + this.currentLevel, new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.09f), Color.BLACK));
        Label enemiesRemaining = new Label("Enemies Remaining: " + enemiesRemaining(), new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.09f), Color.BLACK));
        Label timeLabel = new Label("Time Remaining: " + timeRemaining, new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.09f), Color.BLACK));

        Container<Label> level = new Container<>(currentLevel), enemies = new Container<>(enemiesRemaining), time = new Container<>(timeLabel);

        level.setTransform(true);
        enemies.setTransform(true);
        time.setTransform(true);

        level.setPosition(20, FRUSTUM_HEIGHT - 5f);
        enemies.setPosition(20, FRUSTUM_HEIGHT - 10f);
        time.setPosition(20, FRUSTUM_HEIGHT - 15f);

        time.validate();
        enemies.validate();
        level.validate();

        InGameScreenGuiManager.registerGui("level", level, actor -> ((Container<Label>) actor).getActor().setText("Current Wave: " + (this.currentLevel / 10 + 1) + "-" + (this.currentLevel % 10)));
        InGameScreenGuiManager.registerGui("enemies", enemies, actor -> ((Container<Label>) actor).getActor().setText("Enemies Remaining: " + enemiesRemaining()));
        InGameScreenGuiManager.registerGui("time", time, actor -> ((Container<Label>) actor).getActor().setText("Time Remaining: " + ((int) (timeRemaining))));
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
        if (aliveObstacles.remove(e)) {
            if (!isLosing) {
                checkLevelUp();
            }
        }
    }

    public void addEnemy(Entity e) {
        aliveEntities.add(e);
    }

    public void addObstacle(Entity e) {
        aliveObstacles.add(e);
    }

    private void checkLevelUp() {
        if (enemiesRemaining() == 0) {
            currentLevel++;
            Level newLevel = getCurrentLevel();
            WallEntity.reloadWalls(newLevel.levelWidth(), newLevel.levelHeight());
            spawner.spawn(newLevel);
            timeRemaining = newLevel.time();
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevel - 1);
    }

    public void killAllObjects() {
        for (Entity e : aliveEntities) {
            LateRemover.tagToRemove(e);
        }
        for (Entity e : aliveObstacles) {
            LateRemover.tagToRemove(e);
        }
        aliveEntities.clear();
        aliveObstacles.clear();
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
        killAllObjects();
        if (currentLevel != 0) {
            currentLevel--;
        }
        checkLevelUp();
        isLosing = false;
    }

}
