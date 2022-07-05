package me.partlysunny.shapewars.item.items.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.ItemFactory;
import me.partlysunny.shapewars.util.factories.PlayerMeleeAttackFactory;

public class WoodenStick implements WeaponItem {
    @Override
    public int maxUses() {
        return -1;
    }

    @Override
    public float usesRegenRate() {
        return 0;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Wooden Stick";
    }

    @Override
    public String description() {
        return "Just an ordinary stick.";
    }

    @Override
    public String texture() {
        return "woodenStick";
    }

    @Override
    public float renderSizeX() {
        return 5;
    }

    @Override
    public float renderSizeY() {
        return 10;
    }

    @Override
    public int damage() {
        return 5;
    }

    @Override
    public float attackDelay() {
        return 0.5f;
    }

    @Override
    public void attack(Entity attacker) {
        VisualEffectManager.getEffect("playerSwing").playEffect(InGameScreen.playerInfo.equipment().shownWeapon());
        float rotation = Mappers.transformMapper.get(attacker).rotation * MathUtils.radiansToDegrees;
        InGameScreen.world.gameWorld().addEntity(PlayerMeleeAttackFactory.getInstance().generateMeleeAttack(rotation, 120, 4, damage()));
    }
}
