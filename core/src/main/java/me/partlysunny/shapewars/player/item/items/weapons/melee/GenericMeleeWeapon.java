package me.partlysunny.shapewars.player.item.items.weapons.melee;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.ItemFactory;
import me.partlysunny.shapewars.util.factories.PlayerMeleeAttackFactory;

public abstract class GenericMeleeWeapon implements WeaponItem {
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

    protected abstract int coverAngle();

    protected abstract float life();

    @Override
    public void attack(Entity attacker) {
        VisualEffectManager.getEffect("playerSwing").playEffect(InGameScreen.playerInfo.equipment().shownWeapon());
        float rotation = Mappers.transformMapper.get(attacker).rotation * MathUtils.radiansToDegrees;
        InGameScreen.world.gameWorld().addEntity(PlayerMeleeAttackFactory.getInstance().generateMeleeAttack(rotation, coverAngle(), 4, damage(), life()));
    }
}
