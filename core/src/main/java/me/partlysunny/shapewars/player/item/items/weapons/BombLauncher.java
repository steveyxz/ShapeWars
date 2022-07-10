package me.partlysunny.shapewars.player.item.items.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.factories.BombFactory;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class BombLauncher implements WeaponItem {
    @Override
    public int maxUses() {
        return 10;
    }

    @Override
    public float usesRegenRate() {
        return 2;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Bomb Launcher";
    }

    @Override
    public String description() {
        return "Launches bombs I guess?";
    }

    @Override
    public String texture() {
        return "bombLauncher";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 12;
    }

    @Override
    public float attackDelay() {
        return 1;
    }

    @Override
    public void attack(Entity attacker) {
        BombFactory.getInstance().generateBomb(attacker, 2, 5, BulletRestrictions.ONLY_ENTITIES, 15, 400, "playerShoot", "basicBomb", "basicExplode");
    }
}
