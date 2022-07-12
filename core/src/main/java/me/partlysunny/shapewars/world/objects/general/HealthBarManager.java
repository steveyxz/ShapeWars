package me.partlysunny.shapewars.world.objects.general;

public class HealthBarManager {

    private static final HealthBarPool pool = new HealthBarPool(100, 10000);

    public static HealthBarPool pool() {
        return pool;
    }

}
