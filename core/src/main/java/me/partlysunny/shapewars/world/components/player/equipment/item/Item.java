package me.partlysunny.shapewars.world.components.player.equipment.item;

import com.badlogic.ashley.core.Entity;

public interface Item {

    Entity itemEntity();

    String name();

    String description();

}
