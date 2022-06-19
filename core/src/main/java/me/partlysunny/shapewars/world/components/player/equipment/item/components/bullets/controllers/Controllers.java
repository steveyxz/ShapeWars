package me.partlysunny.shapewars.world.components.player.equipment.item.components.bullets.controllers;

import me.partlysunny.shapewars.util.ContactDispatcher;

public final class Controllers {

    public static final BasicController BASIC = new BasicController();

    public static void init() {
        ContactDispatcher.registerListener(BASIC);
    }

}
