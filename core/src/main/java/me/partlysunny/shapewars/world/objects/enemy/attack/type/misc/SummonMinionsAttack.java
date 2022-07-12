package me.partlysunny.shapewars.world.objects.enemy.attack.type.misc;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.objects.enemy.EnemyManager;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;
import me.partlysunny.shapewars.world.objects.enemy.type.Enemy;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import static me.partlysunny.shapewars.screens.InGameScreen.levelManager;

public class SummonMinionsAttack implements EnemyAttack {

    private final int min;
    private final int max;
    private final float closestEnemy;
    private final float furthestEnemy;
    private final String enemyTypeToSummon;
    private final Vector2 vec = new Vector2();

    public SummonMinionsAttack(int min, int max, float closestEnemy, float furthestEnemy, String enemyTypeToSummon) {
        this.min = min;
        this.max = max;
        this.closestEnemy = closestEnemy;
        this.furthestEnemy = furthestEnemy;
        this.enemyTypeToSummon = enemyTypeToSummon;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    @Override
    public float maxDistance() {
        return 100;
    }

    @Override
    public void attack(Entity enemyEntity, Entity player) {
        int amount = Util.getRandomBetween(min, max);

        TransformComponent enemyTransform = Mappers.transformMapper.get(enemyEntity);
        Enemy enemyType = EnemyManager.getEnemy(enemyTypeToSummon);

        VisualEffectManager.getEffect("enemySummon").playEffect(enemyEntity);

        for (int i = 0; i < amount; i++) {
            Util.getRandomPosAround(vec, enemyTransform.position.x, enemyTransform.position.y, furthestEnemy, closestEnemy);
            Entity enemy = enemyType.createEntity(InGameScreen.world.gameWorld(), vec.x, vec.y);
            levelManager.addEnemy(enemy);
            ParticleEffectManager.startEffect("enemySpawnIn", (int) TextureRenderingSystem.metersToPixels(vec.x), (int) TextureRenderingSystem.metersToPixels(vec.y), 50);
        }
    }

    @Override
    public float cooldown() {
        return 18;
    }
}
