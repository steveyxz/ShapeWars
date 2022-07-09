package me.partlysunny.shapewars.world.components.enemy.loot;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.utilities.Util;

public class LootPickupHandle implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Pair<Entity, Entity> result = Util.handleLootItemPickup(contact);
        if (result != null) {
            Mappers.lootItemMapper.get(result.a()).pickup();
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
