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

public class SwarmEnemyCore extends Enemy {
    @Override
    protected float getHealth() {
        return 600;
    }

    @Override
    protected String getTexture() {
        return "swarmEnemyCore";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 6;
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
        return AttackSets.SWARMER;
    }

    @Nullable
    @Override
    protected CustomLootTable loot() {
        return new CustomLootTable.LootTableBuilder().setRolls(3).addEntry(new TableEntryWrapper(new CoinEntry(20, 30, 5), 2)).addEntry(new TableEntryWrapper(new UtilityItemEntry(1, 2, "basicAmmoPack"), 1)).build();
    }

    @Override
    protected EnemyBehaviour behaviour() {
        return EnemyBehaviour.ESCAPE;
    }

    @Override
    protected float viewRange() {
        return 50;
    }
}
