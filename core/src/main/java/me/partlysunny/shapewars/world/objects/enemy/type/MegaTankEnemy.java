package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import me.partlysunny.shapewars.util.constants.AttackSets;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.item.UtilityItemEntry;
import me.partlysunny.shapewars.world.components.enemy.loot.table.CustomLootTable;
import me.partlysunny.shapewars.world.components.enemy.loot.table.TableEntryWrapper;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import javax.annotation.Nullable;

public class MegaTankEnemy extends Enemy {
    @Override
    protected float getHealth() {
        return 100;
    }

    @Override
    protected String getTexture() {
        return "megaTankEnemy";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 9;
    }

    @Override
    protected Shape getShape(float width) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, width / 2f);
        return shape;
    }

    @Override
    protected float getMaxSpeed() {
        return 4;
    }

    @Override
    protected EnemyAttackSelector selector() {
        return AttackSets.MEGA_TANKY_ATTACK;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return CustomLootTable.LootTableBuilder.builder().setRolls(3).addEntry(new TableEntryWrapper(new CoinEntry(2, 4, 2), 3)).addEntry(new TableEntryWrapper(new UtilityItemEntry(1, 1, "basicHpPot"), 1)).build();
    }
}