package me.partlysunny.shapewars.world.components.player.equipment.item.types;

import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.world.components.player.equipment.item.HasUses;
import me.partlysunny.shapewars.world.components.player.equipment.item.Item;

public interface WeaponItem extends Item, HasUses {

    int damage();

    void attack(Vector2 pos, float angle);

}
