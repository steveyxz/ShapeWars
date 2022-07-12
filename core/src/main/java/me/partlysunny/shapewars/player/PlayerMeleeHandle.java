package me.partlysunny.shapewars.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.components.player.PlayerMeleeAttackComponent;

public class PlayerMeleeHandle implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = Util.handleBasicPlayerMeleeCollision(contact);
        if (result != null) {
            Entity attack = result.b();
            Entity enemy = result.a();

            PlayerMeleeAttackComponent meleeAttack = Mappers.playerMeleeAttackMapper.get(attack);
            if (meleeAttack.canHit(enemy)) {
                Util.playDamage(enemy, meleeAttack.damage());
                Util.doKnockback(enemy, 15);
                meleeAttack.hit(enemy);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //Nothing
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Nothing
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //Nothing
    }
}
