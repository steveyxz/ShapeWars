package me.partlysunny.shapewars.bullets.controllers;

import me.partlysunny.shapewars.util.classes.ContactDispatcher;

public final class Controllers {

    public static final BasicController BASIC = new BasicController();

    public static void init() {
        ContactDispatcher.registerListener(BASIC);
    }

}
