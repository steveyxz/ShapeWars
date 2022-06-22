package me.partlysunny.shapewars.world.components.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

public class EnemyAttackComponent implements Component, Pool.Poolable {

    private EnemyAttackSelector selector = null;
    private Entity parent;
    private float attackCooldown = 0;

    public void init(EnemyAttackSelector selector, Entity parent) {
        this.selector = selector;
        this.parent = parent;
    }

    public void update(float delta) {
        if (attackCooldown == 0) {
            EnemyAttack attack = selector.tryAttack(parent);
            if (attack != null) {
                attackCooldown = attack.cooldown();
            }
        }
        attackCooldown -= delta;
        if (attackCooldown < 0) {
            attackCooldown = 0;
        }
    }

    @Override
    public void reset() {
        selector = null;
        attackCooldown = 0;
        parent = null;
    }
}
