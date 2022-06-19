package me.partlysunny.shapewars.item.types;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.item.HasUses;

public interface WeaponItem extends Item, HasUses {

    int damage();

    float attackDelay();

    void attack(Entity attacker);

}
