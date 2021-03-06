package me.partlysunny.shapewars.util.factories;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public final class Box2DFactory {

    public static Box2DFactory INSTANCE;

    public static Box2DFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Box2DFactory();
        }
        return INSTANCE;
    }

    public FixtureDef generateFixture(Material material, Shape shape) {
        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.friction = material.friction;
        def.density = material.density;
        def.restitution = material.restitution;
        return def;
    }

    public enum Material {
        BOUNCY(0.01f, 0.5f, 0.7f),
        SOLID(0.02f, 0.7f, 0),
        LIGHT(0.002f, 0.3f, 0),
        SLIPPERY(0.01f, 0.01f, 0),
        ;

        private final float density;
        private final float friction;
        private final float restitution;

        Material(float density, float friction, float restitution) {
            this.density = density;
            this.friction = friction;
            this.restitution = restitution;
        }

        public float density() {
            return density;
        }

        public float friction() {
            return friction;
        }

        public float restitution() {
            return restitution;
        }
    }


}
