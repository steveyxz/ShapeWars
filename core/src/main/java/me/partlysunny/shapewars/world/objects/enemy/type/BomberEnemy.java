package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import javax.annotation.Nullable;

public class BomberEnemy extends Enemy {
    @Override
    protected float getHealth() {
        return 50;
    }

    @Override
    protected String getTexture() {
        return "bomberEnemy";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 5;
    }

    @Override
    protected Shape getShape(float width) {
        return Util.generateShape(6, width / 2f);
    }

    @Override
    protected float getMaxSpeed() {
        return 9;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.BASIC_BOMBER;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return null;
    }
}
