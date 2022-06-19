package me.partlysunny.shapewars.item.types;

import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.item.Item;

public interface ArmorItem extends Item {

    float getProtection();

    void onHit(Vector2 position);

}
