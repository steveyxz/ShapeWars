package me.partlysunny.shapewars.world.components.player.equipment.item.items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.world.components.player.equipment.item.types.WeaponItem;

public class CircleBlaster implements WeaponItem {
    @Override
    public int maxUses() {
        return 20;
    }

    @Override
    public long usesRegenRate() {
        return 1;
    }

    @Override
    public Entity itemEntity() {
        return null;
    }

    @Override
    public String name() {
        return "Circle Blaster";
    }

    @Override
    public String description() {
        return "Blasts circles";
    }

    @Override
    public int damage() {
        return 5;
    }

    @Override
    public void attack(Vector2 pos, float angle) {

    }
}
