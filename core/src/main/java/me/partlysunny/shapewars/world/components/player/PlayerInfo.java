package me.partlysunny.shapewars.world.components.player;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.equipment.PlayerEquipment;

public class PlayerInfo {

    private final Entity playerEntity;
    private float health = GameInfo.PLAYER_MAX_HEALTH;
    private PlayerEquipment equipment = new PlayerEquipment();

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

    public TransformComponent getTransformComponent() {
        TransformComponent component = playerEntity.getComponent(TransformComponent.class);
        if (component != null) {
            return component;
        }
        throw new IllegalArgumentException("Player entity does not have transform component!");
    }
}
