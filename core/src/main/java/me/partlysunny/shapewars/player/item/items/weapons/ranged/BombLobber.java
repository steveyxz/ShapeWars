package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.factories.BombFactory;
import me.partlysunny.shapewars.util.factories.ItemFactory;

public class BombLobber implements WeaponItem {

    @Override
    public int maxUses() {
        return 20;
    }

    @Override
    public float usesRegenRate() {
        return 5f;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Bomb Lobber";
    }

    @Override
    public String description() {
        return "Better than the launcher!";
    }

    @Override
    public String texture() {
        return "bombLobber";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 350;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 40;
    }

    @Override
    public float attackDelay() {
        return 2f;
    }

    @Override
    public void attack(Entity attacker) {
        BombFactory.getInstance().generateBomb(attacker, 4, 5, BulletRestrictions.ONLY_ENTITIES, damage(), 400, "playerShoot", "basicBomb", "basicExplode");
    }

}
