package me.partlysunny.shapewars.world.components.player.equipment.item.types;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.world.components.player.equipment.item.HasUses;
import me.partlysunny.shapewars.world.components.player.equipment.item.Item;

public interface WeaponItem extends Item, HasUses {

    int damage();

    float attackDelay();

    void attack(Entity attacker);

}
