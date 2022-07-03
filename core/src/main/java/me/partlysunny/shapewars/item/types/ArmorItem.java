package me.partlysunny.shapewars.item.types;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.item.Item;

public interface ArmorItem extends Item {

    float getProtection();

    void onHit(Entity attacker);

    default String getDescription() {
        return name() + "\n" + description() + "\nProtection: " + getProtection();
    }
}
