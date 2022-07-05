package me.partlysunny.shapewars.item.types;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.player.PlayerInfo;

public interface UtilityItem extends Item {

    void use(Entity player);

    default String getDescription() {
        return name() + "\n" + description();
    }

}
