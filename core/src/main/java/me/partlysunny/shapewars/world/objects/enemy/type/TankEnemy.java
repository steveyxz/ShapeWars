package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.item.UtilityItemEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;
import me.partlysunny.shapewars.world.components.enemy.loot.table.TableEntryWrapper;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import javax.annotation.Nullable;

public class TankEnemy extends Enemy {
    @Override
    protected float getHealth() {
        return 60;
    }

    @Override
    protected String getTexture() {
        return "tankEnemy";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 7;
    }

    @Override
    protected Shape getShape(float width) {
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f);
        return shape;
    }

    @Override
    protected float getMaxSpeed() {
        return 5;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.FAST_TANKY_ATTACK;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return CustomLootTable.LootTableBuilder.builder().setRolls(2).addEntry(new TableEntryWrapper(new CoinEntry(2, 4, 2), 3)).addEntry(new TableEntryWrapper(new UtilityItemEntry(1, 1, "basicHpPot"), 1)).build();
    }
}
