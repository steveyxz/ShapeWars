package me.partlysunny.shapewars.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import me.partlysunny.shapewars.world.objects.obstacle.WallEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    }

    private void loadLevels() {
        FileHandle levels = Gdx.files.getFileHandle("levels", Files.FileType.Internal);
        if (levels.isDirectory()) {
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
                    int enemyAmount = contentSection.child.asInt();
                    enemies.put(enemyType, enemyAmount);
                }
                this.levels.add(new Level(levelWidth, levelHeight, enemies));
            }
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
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevel - 1);
    }

    public int enemiesRemaining() {
        return aliveEntities.size();
    }

}
