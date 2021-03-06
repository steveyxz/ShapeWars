package me.partlysunny.shapewars.util.constants;

import me.partlysunny.shapewars.bullets.controllers.enemy.EnemyBlasterBulletController;
import me.partlysunny.shapewars.bullets.controllers.player.BasicPlayerBulletController;
import me.partlysunny.shapewars.bullets.controllers.player.ShotgunPlayerBulletController;
import me.partlysunny.shapewars.bullets.controllers.player.SniperPlayerBulletController;
import me.partlysunny.shapewars.util.classes.ContactDispatcher;

public final class Controllers {

    public static final BasicPlayerBulletController BASIC = new BasicPlayerBulletController();
    public static final EnemyBlasterBulletController ENEMY_BLASTER = new EnemyBlasterBulletController();
    public static final ShotgunPlayerBulletController SHOTGUN = new ShotgunPlayerBulletController();
    public static final SniperPlayerBulletController SNIPER = new SniperPlayerBulletController();

    public static void init() {
        ContactDispatcher.registerListener(BASIC);
        ContactDispatcher.registerListener(ENEMY_BLASTER);
    }

}
