package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyMeleeDamageComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyState;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyStateComponent;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import static me.partlysunny.shapewars.util.utilities.Util.handleBasicEnemyMeleeCollision;

public class MeleeHandle implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = handleBasicEnemyMeleeCollision(contact);
        if (result != null) {
            Entity player = result.b();
            Entity enemy = result.a();

            if (Mappers.enemyMeleeDamageMapper.has(enemy)) {
                EnemyStateComponent enemyState = Mappers.enemyStateMapper.get(enemy);
                if (enemyState.state() == EnemyState.ATTACKING) {
                    RigidBodyComponent playerBody = Mappers.bodyMapper.get(player);
                    EnemyMeleeDamageComponent enemyDamage = Mappers.enemyMeleeDamageMapper.get(enemy);
                    ParticleEffectManager.startEffect("enemyMeleeAttack", (int) TextureRenderingSystem.metersToPixels(playerBody.rigidBody().getPosition().x), (int) TextureRenderingSystem.metersToPixels(playerBody.rigidBody().getPosition().y), 100);
                    VisualEffectManager.getEffect("damage").playEffect(player);
                    InGameScreen.playerInfo.damage(enemyDamage.damage());
                    enemyState.setState(EnemyState.PURSUING, 0.1f);
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}


