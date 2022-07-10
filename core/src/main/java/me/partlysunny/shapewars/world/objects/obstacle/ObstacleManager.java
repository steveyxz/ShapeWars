package me.partlysunny.shapewars.world.objects.obstacle;

import java.util.HashMap;
import java.util.Map;

public class ObstacleManager {

    private static final Map<String, ObstacleEntity> obstacles = new HashMap<>();

    public static void registerObstacle(String id, ObstacleEntity obstacle) {
        obstacles.put(id, obstacle);
    }

    public static ObstacleEntity getObstacle(String id) {
        return obstacles.get(id);
    }

    public static void unregisterObstacle(String id) {
        obstacles.remove(id);
    }

    public static void init() {
        registerObstacle("rock", new RockEntity());
        registerObstacle("crate", new CrateObstacle());
        registerObstacle("tnt", new TntObstacle());
    }

}
