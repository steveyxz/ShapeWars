package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.item.UtilityItemEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;
import me.partlysunny.shapewars.world.components.enemy.loot.table.TableEntryWrapper;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import javax.annotation.Nullable;

public class HexDefenderEnemy extends Enemy {
    @Override
    protected float getHealth() {
        return 500;
    }

    @Override
    protected String getTexture() {
        return "hexDefender";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 8;
    }

    @Override
    protected Shape getShape(float width) {
        return Util.generateShape(6, width / 2f);
    }

    @Override
    protected float getMaxSpeed() {
        return 8;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.HEX_ATTACK;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return new CustomLootTable.LootTableBuilder().setRolls(3).addEntry(new TableEntryWrapper(new CoinEntry(2, 5, 2), 2)).addEntry(new TableEntryWrapper(new UtilityItemEntry(1, 1, "mediumHpPot"), 1)).build();
    }

    @Override
    protected EnemyBehaviour behaviour() {
        return EnemyBehaviour.PURSUE;
    }

    @Override
    protected float viewRange() {
        return 500;
    }
}
