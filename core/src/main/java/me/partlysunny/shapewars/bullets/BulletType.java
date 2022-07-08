package me.partlysunny.shapewars.bullets;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class BulletType {

    private final String texture;
    private final ProjectileHitBox hitbox;

    public BulletType(String texture, ProjectileHitBox hitbox) {
        this.texture = texture;
        this.hitbox = hitbox;
    }

    public ProjectileHitBox hitbox() {
        return hitbox;
    }

    public String texture() {
        return texture;
    }

    public int width() {
        return hitbox.radius;
    }

    public int height() {
        return hitbox.radius;
    }

    public enum ProjectileHitBox {

        BASIC_BULLET(1, 0),
        BLASTER_BULLET(2, 4);

        private final int radius;
        private final int sideCount;

        ProjectileHitBox(int radius, int sideCount) {
            this.radius = radius;
            this.sideCount = sideCount;
        }

        public FixtureDef genDef() {
            FixtureDef def = new FixtureDef();
            Shape shape;
            if (sideCount == 0) {
                shape = new CircleShape();
                shape.setRadius(radius / 2f);
            } else {
                shape = new PolygonShape();
                float[] points = new float[sideCount * 2];
                for (int i = 0; i < sideCount * 2; i += 2) {
                    float angle = (i / 2f) * (360f / sideCount) - ((360f / sideCount) / 2f);
                    float x = radius * MathUtils.cos(angle);
                    float y = radius * MathUtils.sin(angle);
                    points[i] = x;
                    points[i + 1] = y;
                }
                ((PolygonShape) shape).set(points);
            }
            def.shape = shape;
            return def;
        }

        public int radius() {
            return radius;
        }

        public int sideCount() {
            return sideCount;
        }
    }
}
