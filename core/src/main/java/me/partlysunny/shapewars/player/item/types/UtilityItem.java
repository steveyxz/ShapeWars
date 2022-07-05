package me.partlysunny.shapewars.player.item.types;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.player.item.Item;

public interface UtilityItem extends Item {

    void use(Entity player);

    default String getDescription() {
        return name() + "\n" + description();
    }

}
