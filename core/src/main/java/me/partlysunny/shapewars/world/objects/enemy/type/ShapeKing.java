package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import javax.annotation.Nullable;

public class ShapeKing extends Enemy {
    @Override
    protected float getHealth() {
        return 1200;
    }

    @Override
    protected String getTexture() {
        return "shapeKing";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 20;
    }

    @Override
    protected Shape getShape(float width) {
        return Util.generateShape(4, width / 2f);
    }

    @Override
    protected float getMaxSpeed() {
        return 5;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.BOSS;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return new CustomLootTable.LootTableBuilder().build();
    }

    @Override
    protected EnemyBehaviour behaviour() {
        return EnemyBehaviour.PURSUE;
    }

    @Override
    protected float viewRange() {
        return 600;
    }
}
