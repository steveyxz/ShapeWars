package me.partlysunny.shapewars.bullets;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import me.partlysunny.shapewars.util.utilities.Util;

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
            def.shape = Util.generateShape(sideCount, radius);
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
