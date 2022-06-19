package me.partlysunny.shapewars.world.components.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.equipment.PlayerEquipment;

public class PlayerInfo {

    private final Entity playerEntity;
    private float health = GameInfo.PLAYER_MAX_HEALTH;
    private PlayerEquipment equipment = new PlayerEquipment();
    private PlayerKeyMap keyMap = new PlayerKeyMap();
    private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);

    public PlayerInfo(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public float health() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public PlayerEquipment equipment() {
        return equipment;
    }

    public void setEquipment(PlayerEquipment equipment) {
        this.equipment = equipment;
    }

    public Entity playerEntity() {
        return playerEntity;
    }

    public PlayerKeyMap keyMap() {
        return keyMap;
    }

    public void setKeyMap(PlayerKeyMap keyMap) {
        this.keyMap = keyMap;
    }

    public TransformComponent getTransformComponent() {
        if (!transformMapper.has(playerEntity)) {
            throw new IllegalArgumentException("Player entity does not have transform component!");
        }
        return transformMapper.get(playerEntity);
    }
}
