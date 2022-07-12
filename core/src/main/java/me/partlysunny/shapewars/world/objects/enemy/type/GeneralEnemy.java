package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;
import me.partlysunny.shapewars.world.components.enemy.loot.table.TableEntryWrapper;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import javax.annotation.Nullable;

public class GeneralEnemy extends Enemy {
    @Override
    protected float getHealth() {
        return 800;
    }

    @Override
    protected String getTexture() {
        return "generalEnemy";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 10;
    }

    @Override
    protected Shape getShape(float width) {
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f);
        return shape;
    }

    @Override
    protected float getMaxSpeed() {
        return 4;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.BASIC_GENERAL;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return CustomLootTable.LootTableBuilder.builder().setRolls(3).addEntry(new TableEntryWrapper(new CoinEntry(8, 16, 6), 1)).build();
    }

    @Override
    protected EnemyBehaviour behaviour() {
        return EnemyBehaviour.PURSUE;
    }

    @Override
    protected float viewRange() {
        return 250;
    }
}
