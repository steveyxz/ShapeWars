package me.partlysunny.shapewars.player.item.items.weapons.ranged;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.BombFactory;
import me.partlysunny.shapewars.util.factories.ItemFactory;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

public class BombSprayer implements WeaponItem {

    private static final float angleDiff = 20 * MathUtils.degreesToRadians;

    @Override
    public int maxUses() {
        return 20;
    }

    @Override
    public float usesRegenRate() {
        return 4;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Bomb Sprayer";
    }

    @Override
    public String description() {
        return "Shoots 4 mini bombs!";
    }

    @Override
    public String texture() {
        return "bombSprayer";
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
        return 7;
    }

    @Override
    public float attackDelay() {
        return 2f;
    }

    @Override
    public void attack(Entity attacker) {
        TransformComponent t = Mappers.transformMapper.get(attacker);
        float r = t.rotation;

        bomb(attacker, r - angleDiff * 3 / 2);
        bomb(attacker, r - angleDiff * 1 / 2);
        bomb(attacker, r + angleDiff * 1 / 2);
        bomb(attacker, r + angleDiff * 3 / 2);

        SoundEffectManager.play("playerShoot", 1);
    }

    private void bomb(Entity attacker, float angle) {
        TransformComponent t = Mappers.transformMapper.get(attacker);
        t.rotation = angle;
        BombFactory.getInstance().generateBomb(attacker, 1, 5, BulletRestrictions.ONLY_ENTITIES, damage(), 400, null, "basicBomb", "basicExplode");
    }
}
