package me.partlysunny.shapewars.world.objects.general;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import me.partlysunny.shapewars.world.components.collision.DeletionListenerComponent;
import me.partlysunny.shapewars.world.components.render.ActorComponent;

public final class HealthBarFactory {

    public static Entity createEntity(PooledEngine w, Entity parent) {
        Entity healthBar = w.createEntity();

        HealthBarActor actor = HealthBarManager.pool().obtainHealthBar(parent);
        actor.setSize(8, 1);
        ActorComponent actorComponent = w.createComponent(ActorComponent.class);
        actorComponent.init(actor);
        healthBar.add(actorComponent);

        DeletionListenerComponent deletion = w.createComponent(DeletionListenerComponent.class);
        deletion.init(e -> {
            actor.remove();
        });
        healthBar.add(deletion);

        return healthBar;
    }
}
