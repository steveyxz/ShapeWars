package me.partlysunny.shapewars.world.objects.enemy.type;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class BasicEnemy extends Enemy {
    @Override
    protected float getHealth() {
        return 1;
    }

    @Override
    protected String getTexture() {
        return "basic";
    }

    @Override
    protected float getFrictionalResistance() {
        return 0;
    }

    @Override
    protected float getWidth() {
        return 3;
    }

    @Override
    protected Shape getShape(float width) {
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f);
        return shape;
    }

    @Override
    protected float getMaxSpeed() {
        return 10;
    }
}
