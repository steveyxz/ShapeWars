package me.partlysunny.shapewars.item.types;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.item.HasUses;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.screens.InGameScreen;

public interface WeaponItem extends Item, HasUses {

    int damage();

    float attackDelay();

    void attack(Entity attacker);

    default String getDescription() {
        return name() + "\n" + description() + "\nDamage: " + damage() + "\nAttack Delay: " + attackDelay() + (maxUses() == -1 ? "" : "\nAmmo Remaining: " + InGameScreen.playerInfo.ammoManager().ammoRemaining(texture()));
    }

}
