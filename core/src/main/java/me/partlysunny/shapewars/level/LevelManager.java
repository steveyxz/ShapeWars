package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.PositionSet;
import me.partlysunny.shapewars.util.constants.FontPresets;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemySpawnIndicatorComponent;
import me.partlysunny.shapewars.world.objects.obstacle.WallEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_HEIGHT;
import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.FRUSTUM_WIDTH;

public class LevelManager {

    private final Stage guiStage;
    private final LevelSpawner spawner = new LevelSpawner(this);
    private final List<Entity> aliveEntities = new ArrayList<>();
    private final List<Entity> aliveObstacles = new ArrayList<>();
    private final List<Level> levels = new ArrayList<>();
    private final PositionSet positions = new PositionSet();
    public boolean isLosing = false;
    private int currentLevel = 0;
    private float timeRemaining = 0;
    private boolean isSpawning = false;
    private float waveSpawnCountdown = 0;
    private float stageSpawnCountdown = 0;
    private int currentStage = 0;
    private boolean isCounting = false;

    public LevelManager(Stage guiStage) {
        loadLevels();
        this.guiStage = guiStage;
        initGui();
        checkLevelUp();
    }

    private void initGui() {

        Util.loadVisUI();

        Label currentLevel = new Label("Current Level: " + this.currentLevel, new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.09f), Color.BLACK));
        Label enemiesRemaining = new Label("Enemies Remaining: " + enemiesRemaining(), new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.09f), Color.BLACK));
        Label timeLabel = new Label("Time Remaining: " + timeRemaining, new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_MEDIUM, 0.09f), Color.BLACK));
        Label countdown = new Label("Next Wave In: " + waveSpawnCountdown, new Label.LabelStyle(FontPresets.getFontWithSize(FontPresets.RALEWAY_BOLD, 0.2f), Color.BLACK));

        Container<Label> level = new Container<>(currentLevel), enemies = new Container<>(enemiesRemaining), time = new Container<>(timeLabel), counter = new Container<>(countdown);

        level.setTransform(true);
        enemies.setTransform(true);
        time.setTransform(true);
        counter.setTransform(true);

        level.setPosition(20, FRUSTUM_HEIGHT - 5f);
        enemies.setPosition(20, FRUSTUM_HEIGHT - 10f);
        time.setPosition(20, FRUSTUM_HEIGHT - 15f);
        counter.setPosition(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f);

        time.validate();
        enemies.validate();
        level.validate();
        counter.validate();

        InGameScreen.guiManager.registerGui("level", level, actor -> ((Container<Label>) actor).getActor().setText("Current Wave: " + (this.currentLevel / 10 + 1) + "-" + (this.currentLevel % 10)));
        InGameScreen.guiManager.registerGui("enemies", enemies, actor -> ((Container<Label>) actor).getActor().setText("Enemies Remaining: " + enemiesRemaining()));
        InGameScreen.guiManager.registerGui("time", time, actor -> ((Container<Label>) actor).getActor().setText("Time Remaining: " + ((int) (timeRemaining))));
        InGameScreen.guiManager.registerGui("levelCountdown", counter, actor -> {
            Label countdownLabel = ((Container<Label>) actor).getActor();
            if (isCounting) {
                int shownValue = (int) Math.ceil(waveSpawnCountdown);
                if (countdownLabel.getText().length != 0) {
                    if (Integer.parseInt(String.valueOf(countdownLabel.getText().charAt(countdownLabel.getText().length - 1))) != shownValue) {
                        SoundEffectManager.play("countdown", 1);
                    }
                } else {
                    SoundEffectManager.play("countdown", 1);
                }
                countdownLabel.setText("Next Wave In: " + shownValue);
            } else {
                countdownLabel.setText("");
            }
            counter.setPosition(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f);
        });
    }

    private void loadLevels() {
        FileHandle levels = Gdx.files.internal("assets/levels");
        for (FileHandle file : levels.list()) {
            JsonReader reader = new JsonReader();
            JsonValue value = reader.parse(file.reader());
            int levelWidth = value.getInt("levelWidth");
            int levelHeight = value.getInt("levelHeight");
            int time = value.getInt("time");
            List<LevelStage> enemies = new ArrayList<>();
            Map<String, Integer> obstacles = new HashMap<>();
            JsonValue levelEnemies = value.get("stages");
            for (JsonValue contentSection : levelEnemies) {
                Map<String, Integer> stageEnemies = new HashMap<>();
                for (JsonValue contentEntry : contentSection) {
                    String enemyType = contentEntry.name;
                    int enemyAmount = contentSection.getInt(contentEntry.name);
                    stageEnemies.put(enemyType, enemyAmount);
                }
                enemies.add(new LevelStage(stageEnemies));
            }
            JsonValue levelObstacles = value.get("obstacles");
            for (JsonValue contentSection : levelObstacles) {
                String obstacleType = contentSection.name;
                int obstacleAmount = levelObstacles.getInt(contentSection.name);
                obstacles.put(obstacleType, obstacleAmount);
            }
            this.levels.add(new Level(time, levelWidth, levelHeight, enemies, obstacles));
        }
        for (Level l : this.levels) {
            for (LevelStage stage : l.stages()) {
                stage.setParent(l);
            }
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

    private void regeneratePositions() {
        positions.resetPositions();
        for (int i = 0; i < getCurrentStage().enemyCount(); i++) {
            Util.getPositionInLevelAwayFromCenter(positions, 15, getCurrentLevel());
        }
    }

    private void checkLevelUp() {
        //If no enemies
        if (enemiesRemaining() == 0) {
            //And is not killing off enemies / in spawn stage / counting down
            if (!isLosing && !isSpawning && !isCounting) {
                //If not at really beginning and not at the final stage
                if (currentLevel != 0 && currentStage < getCurrentLevel().stages().size() - 1) {
                    //Increment stage
                    currentStage++;
                    killAllIndicators();
                    regeneratePositions();
                    insertIndicators();
                    startStageSpawn();
                } else {
                    //Increment level
                    currentLevel++;
                    loadCurrentLevel();
                }
            }
        }
    }

    private void loadCurrentLevel() {
        Level newLevel = getCurrentLevel();
        WallEntity.reloadWalls(newLevel.levelWidth(), newLevel.levelHeight());
        waveSpawnCountdown = 3;
        isCounting = true;
        timeRemaining = newLevel.time();
    }

    private void insertIndicators() {
        spawner.spawnIndicators(positions);
    }

    private void startStageSpawn() {
        isSpawning = true;
        stageSpawnCountdown = 2;
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevel - 1);
    }

    public LevelStage getCurrentStage() {
        return getCurrentLevel().stages().get(currentStage);
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

    public void killAllIndicators() {
        for (Entity e : InGameScreen.world.gameWorld().getEntitiesFor(Family.all(EnemySpawnIndicatorComponent.class).get())) {
            LateRemover.tagToRemove(e);
        }
    }

    public int enemiesRemaining() {
        return aliveEntities.size();
    }

    public void update(float delta) {
        if (!isLosing) {
            if (isCounting) {
                waveSpawnCountdown -= delta;
                if (waveSpawnCountdown < 0) {
                    killAllObjects();
                    spawner.spawnObstacles(getCurrentLevel());
                    isCounting = false;
                    waveSpawnCountdown = 0;
                    currentStage = 0;
                    startStageSpawn();
                    regeneratePositions();
                    insertIndicators();
                    SoundEffectManager.play("levelStart", 1);
                }
            } else if (isSpawning) {
                stageSpawnCountdown -= delta;
                if (stageSpawnCountdown < 0) {
                    SoundEffectManager.play("enemySpawn", 10);
                    spawner.spawn(getCurrentLevel().stages().get(currentStage), positions);
                    stageSpawnCountdown = 0;
                    isSpawning = false;
                    killAllIndicators();
                }
            } else {
                timeRemaining -= delta;
                if (timeRemaining < 0) {
                    lossReset();
                    InGameScreen.playerInfo.playerEntity().getComponent(RigidBodyComponent.class).rigidBody().setTransform(0, 0, 0);
                }
            }
            checkLevelUp();
        }
    }

    public void lossReset() {
        isLosing = true;
        killAllObjects();
        if (currentLevel != 1) {
            currentLevel--;
        }
        currentStage = 0;
        isLosing = false;
        loadCurrentLevel();
    }

}
