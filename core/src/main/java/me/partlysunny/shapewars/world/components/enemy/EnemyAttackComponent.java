package me.partlysunny.shapewars.world.components.enemy;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttackSelector;

import java.util.HashMap;
import java.util.Map;

public class EnemyAttackComponent implements Component, Pool.Poolable {

    private final Map<EnemyAttack, Float> attackCooldowns = new HashMap<>();
    private EnemyAttackSelector selector = null;
    private Entity parent = null;

    public void init(EnemyAttackSelector selector, Entity parent) {
        this.selector = selector;
        this.parent = parent;
        for (EnemyAttack attack : selector.possibleAttacks()) {
            attackCooldowns.put(attack, attack.cooldown());
        }
    }

    public void update(float delta) {
        for (EnemyAttack a : attackCooldowns.keySet()) {
            if (attackCooldowns.get(a) == 0) {
                if (selector.tryAttack(parent, a) != null) {
                    attackCooldowns.put(a, a.cooldown());
                }
            }
            attackCooldowns.put(a, attackCooldowns.get(a) - delta);
            if (attackCooldowns.get(a) < 0) {
                attackCooldowns.put(a, 0f);
            }
        }
    }

    @Override
    public void reset() {
        selector = null;
        parent = null;
        attackCooldowns.clear();
    }
}
