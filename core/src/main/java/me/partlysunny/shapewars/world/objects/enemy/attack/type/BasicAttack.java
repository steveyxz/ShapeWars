package me.partlysunny.shapewars.world.objects.enemy.attack.type;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.objects.enemy.attack.EnemyAttack;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

public class BasicAttack extends EnemyAttack {

    private Vector2 vel = new Vector2();

    public BasicAttack(int weight) {
        super(weight);
    }

    @Override
    protected float maxDistance() {
        return 15;
    }

    protected int damage() {
        return 5;
    }

    protected float dashFactor() {
        return 10;
    }

    @Override
    protected void attack(Entity enemyEntity, Entity player) {
        RigidBodyComponent enemyBody = bodyMapper.get(enemyEntity);
        RigidBodyComponent playerBody = bodyMapper.get(player);
        //Dash at the player
        float dashFactor = dashFactor();
        float x = (playerBody.rigidBody().getPosition().x - enemyBody.rigidBody().getPosition().x) * dashFactor;
        float y = (playerBody.rigidBody().getPosition().y - enemyBody.rigidBody().getPosition().y) * dashFactor;
        vel.set(x, y);
        enemyBody.rigidBody().setLinearVelocity(vel.x, vel.y);
        //Damage the player
        InGameScreen.playerInfo.damage(damage());
        ParticleEffectManager.startEffect("enemyMeleeAttack", (int) TextureRenderingSystem.metersToPixels(playerBody.rigidBody().getPosition().x), (int) TextureRenderingSystem.metersToPixels(playerBody.rigidBody().getPosition().y), 100);
    }

    @Override
    public float cooldown() {
        return 2f;
    }
}
