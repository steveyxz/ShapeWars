package me.partlysunny.shapewars.world.components.player.equipment.item.types;

import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.world.components.player.equipment.item.Item;

public interface ArmorItem extends Item {

    float getProtection();

    void onHit(Vector2 position);

}
