package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.factories.BombFactory;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class MachineBomber implements WeaponItem {
    @Override
    public int maxUses() {
        return 30;
    }

    @Override
    public float usesRegenRate() {
        return 1f;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Machine Bomber";
    }

    @Override
    public String description() {
        return "Boom boom boom!";
    }

    @Override
    public String texture() {
        return "machineBomber";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 500;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 8;
    }

    @Override
    public float attackDelay() {
        return 0.4f;
    }

    @Override
    public void attack(Entity attacker) {
        BombFactory.getInstance().generateBomb(attacker, 1, 5, BulletRestrictions.ONLY_ENTITIES, damage(), 400, "playerShoot", "basicBomb", "softFastExplode");
    }

}
