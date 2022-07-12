package me.partlysunny.shapewars.world.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

public class ActorComponent implements Component, Pool.Poolable {

    private Actor actor;

    public void init(Actor actor) {
        this.actor = actor;
    }

    public Actor actor() {
        return actor;
    }

    public void update(float delta, Batch batch) {
        actor.act(delta);
        actor.draw(batch, 1);
    }

    @Override
    public void reset() {
        actor = null;
    }
}
