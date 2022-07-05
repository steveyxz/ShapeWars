package me.partlysunny.shapewars.world.objects.enemy.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyMeleeDamageComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyState;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyStateComponent;

import static me.partlysunny.shapewars.util.utilities.Util.handleBasicEnemyMeleeCollision;

public class MeleeHandle implements ContactListener {

    private final Vector3 f = new Vector3();

    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = handleBasicEnemyMeleeCollision(contact);
        if (result != null) {
            Entity player = result.b();
            Entity enemy = result.a();

            if (Mappers.enemyMeleeDamageMapper.has(enemy)) {
                EnemyStateComponent enemyState = Mappers.enemyStateMapper.get(enemy);
                if (enemyState.state() == EnemyState.ATTACKING) {
                    EnemyMeleeDamageComponent enemyDamage = Mappers.enemyMeleeDamageMapper.get(enemy);
                    InGameScreen.playerInfo.damage(enemyDamage.damage());
                    enemyState.setState(EnemyState.PURSUING);
                    f.set(Mappers.transformMapper.get(player).position);
                    f.sub(Mappers.transformMapper.get(enemy).position);
                    f.scl(-10);
                    Mappers.bodyMapper.get(enemy).rigidBody().applyForceToCenter(f.x, f.y, true);
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


