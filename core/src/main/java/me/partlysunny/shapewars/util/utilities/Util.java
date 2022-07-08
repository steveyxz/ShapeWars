package me.partlysunny.shapewars.util.utilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.StringBuilder;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.widget.Tooltip;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.level.Level;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.classes.Pair;
import me.partlysunny.shapewars.util.classes.PositionSet;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.enemy.EnemyState;
import me.partlysunny.shapewars.world.components.enemy.EnemyStateComponent;
import me.partlysunny.shapewars.world.components.player.PlayerMeleeAttackComponent;
import me.partlysunny.shapewars.world.components.render.DeathEffectComponent;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();
    private static final GlyphLayout layout = new GlyphLayout();
    private static final Vector2 vec = new Vector2();

    public static int getRandomBetween(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be higher than b");
        }
        if (a == b) {
            return a;
        }
        if (a < 0 && b < 0) {
            return -getRandomBetween(-b, -a);
        }
        if (a < 0) {
            return getRandomBetween(0, -a + b) + a;
        }
        return RAND.nextInt(b - a) + a;
    }

    public static void loadVisUI() {
        if (!VisUI.isLoaded()) {
            VisUI.load(new Skin(Gdx.files.internal("assets/flatEarth/flat-earth-ui.json")));
        }
    }

    public static void formatTooltip(Tooltip t) {
        t.getContentCell().align(Alignment.CENTER.getAlignment());
        if (t.getContent() instanceof Label) {
            ((Label) t.getContent()).setAlignment(Alignment.CENTER.getAlignment());
            ((Label) t.getContent()).setWrap(true);
            StringBuilder text = ((Label) t.getContent()).getText();
            layout.setText(((Label) t.getContent()).getStyle().font, text);
            float height = layout.height;
            t.getContentCell().padBottom(height * 3f);
        }
        if (t.getContent() instanceof Table) {
            for (Actor a : ((Table) t.getContent()).getChildren()) {
                ((Label) a).setAlignment(Alignment.CENTER.getAlignment());
                ((Label) a).setWrap(true);
            }
            t.getContentCell().padBottom(t.getContent().getHeight());
        }
        t.setSize(30, 30);
        t.getContentCell().width(26);
    }

    /**
     * Handle basic collision, will not damage the entities, only validate
     *
     * @return A pair of the bullet and the entity or null if invalid
     */
    @Nullable
    public static Pair<Entity, Entity> handleBasicPlayerBulletCollision(Contact contact) {
        GameWorld world = InGameScreen.world;
        Entity a = world.getEntityWithRigidBody(contact.getFixtureA().getBody());
        Entity b = world.getEntityWithRigidBody(contact.getFixtureB().getBody());
        if (a == null || b == null) {
            return null;
        }
        if (a.isScheduledForRemoval() || a.isRemoving() || b.isScheduledForRemoval() || b.isRemoving()) {
            return null;
        }
        Entity bullet = null;
        Entity other = null;
        if (Mappers.bulletMapper.has(a)) {
            bullet = a;
            other = b;
        }
        if (Mappers.bulletMapper.has(b)) {
            if (bullet != null) {
                LateRemover.tagToRemove(a);
                LateRemover.tagToRemove(b);
            }
            bullet = b;
            other = a;
        }
        if (other == null) {
            return null;
        } else if (!Mappers.healthMapper.has(other) || Mappers.controlMapper.has(other) || Mappers.bulletMapper.get(bullet).restrictions() == BulletRestrictions.ONLY_PLAYERS) {
            if (Mappers.deletionMapper.has(other)) {
                LateRemover.tagToRemove(bullet);
            }
            return null;
        }
        return new Pair<>(bullet, other);
    }

    @Nullable
    public static Pair<Entity, Entity> handleBasicEnemyBulletCollision(Contact contact) {
        GameWorld world = InGameScreen.world;
        Entity a = world.getEntityWithRigidBody(contact.getFixtureA().getBody());
        Entity b = world.getEntityWithRigidBody(contact.getFixtureB().getBody());
        if (a == null || b == null) {
            return null;
        }
        if (a.isScheduledForRemoval() || a.isRemoving() || b.isScheduledForRemoval() || b.isRemoving()) {
            return null;
        }
        Entity bullet = null;
        Entity player = null;
        if (Mappers.bulletMapper.has(a)) {
            bullet = a;
            player = b;
        }
        if (Mappers.bulletMapper.has(b)) {
            if (bullet != null) {
                LateRemover.tagToRemove(a);
                LateRemover.tagToRemove(b);
            }
            bullet = b;
            player = a;
        }
        if (player == null) {
            return null;
        } else if (Mappers.bulletMapper.get(bullet).restrictions() == BulletRestrictions.ONLY_ENTITIES || !Mappers.controlMapper.has(player)) {
            if (Mappers.obstacleMapper.has(player) && Mappers.bulletMapper.has(bullet)) {
                //If the "player" is an obstacle then just damage it (if bullet is enemy bullet as well)
                Mappers.healthMapper.get(player).addHealth(-Mappers.bulletMapper.get(bullet).damage());
                VisualEffectManager.getEffect("damage").playEffect(player);
            }
            return null;
        }
        return new Pair<>(bullet, player);
    }

    @Nullable
    public static Pair<Entity, Entity> handleBasicPlayerMeleeCollision(Contact contact) {
        GameWorld world = InGameScreen.world;
        Entity a = world.getEntityWithRigidBody(contact.getFixtureA().getBody());
        Entity b = world.getEntityWithRigidBody(contact.getFixtureB().getBody());
        if (a == null || b == null) {
            return null;
        }
        if (a.isScheduledForRemoval() || a.isRemoving() || b.isScheduledForRemoval() || b.isRemoving()) {
            return null;
        }
        Entity playerAttack = null;
        Entity enemy = null;
        if (Mappers.playerMeleeAttackMapper.has(a)) {
            enemy = b;
            playerAttack = a;
        }
        if (Mappers.playerMeleeAttackMapper.has(b)) {
            if (playerAttack != null) {
                return null;
            }
            enemy = a;
            playerAttack = b;
        }
        if (playerAttack == null) {
            return null;
        }
        if (!Mappers.enemyStateMapper.has(enemy)) {
            //If it isn't an enemy just damage it if it has hp
            if (Mappers.healthMapper.has(enemy)) {
                PlayerMeleeAttackComponent attack = Mappers.playerMeleeAttackMapper.get(playerAttack);
                if (attack.canHit(enemy)) {
                    Mappers.healthMapper.get(enemy).addHealth(-attack.damage());
                    VisualEffectManager.getEffect("damage").playEffect(enemy);
                    attack.hit(enemy);
                    doKnockback(enemy, 120);
                }
            }
            //Also delete bullets
            if (Mappers.bulletMapper.has(enemy)) {
                LateRemover.tagToRemove(enemy);
            }
            return null;
        }
        return new Pair<>(playerAttack, enemy);
    }

    @Nullable
    public static Pair<Entity, Entity> handleBasicEnemyMeleeCollision(Contact contact) {
        GameWorld world = InGameScreen.world;
        Entity a = world.getEntityWithRigidBody(contact.getFixtureA().getBody());
        Entity b = world.getEntityWithRigidBody(contact.getFixtureB().getBody());
        if (a == null || b == null) {
            return null;
        }
        if (a.isScheduledForRemoval() || a.isRemoving() || b.isScheduledForRemoval() || b.isRemoving()) {
            return null;
        }
        Entity enemy = null;
        Entity player = null;
        if (Mappers.playerStateMapper.has(a)) {
            player = a;
            enemy = b;
        }
        if (Mappers.playerStateMapper.has(b)) {
            if (enemy != null) {
                return null;
            }
            player = b;
            enemy = a;
        }
        if (player == null || !Mappers.enemyStateMapper.has(enemy)) {
            return null;
        }
        return new Pair<>(enemy, player);
    }

    public static void doKnockback(Entity target, float force) {
        Entity player = InGameScreen.playerInfo.playerEntity();
        if (Mappers.bodyMapper.has(target)) {
            RigidBodyComponent r = Mappers.bodyMapper.get(target);

            TransformComponent playerPos = Mappers.transformMapper.get(player);

            vec.set(r.rigidBody().getPosition().x - playerPos.position.x, r.rigidBody().getPosition().y - playerPos.position.y);
            vec.nor();
            vec.scl(force);

            r.rigidBody().applyForceToCenter(vec, true);

            if (Mappers.enemyStateMapper.has(target)) {
                EnemyStateComponent s = Mappers.enemyStateMapper.get(target);

                s.setState(EnemyState.STATIONARY);
                s.setState(EnemyState.PURSUING, 0.2f);
            }
        }
    }

    public static void playDamage(Entity victim, int damage) {
        HealthComponent health = Mappers.healthMapper.get(victim);
        health.addHealth(-damage);
        VisualEffectManager.getEffect("damage").playEffect(victim);
        if (Mappers.deathEffectMapper.has(victim) && health.health() <= 0) {
            DeathEffectComponent deathEffect = Mappers.deathEffectMapper.get(victim);
            deathEffect.die();
            SoundEffectManager.play("enemyDie", 1);
        }
        SoundEffectManager.play("bulletCollide", 1);
    }

    public static float getVolumeOfSoundFromPos(float playerX, float playerY, float soundX, float soundY, float initialVolume) {
        float distX = soundX - playerX;
        float distY = soundY - playerY;
        float dist = (float) Math.sqrt(Math.abs(distX * distX + distY * distY));
        return 1 / dist * initialVolume;
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.set(MathUtils.cos(angle), MathUtils.sin(angle));
        return outVector;
    }

    public static void getPositionInLevelAwayFromCenter(PositionSet toLoad, float distance, Level level) {
        int x = Util.getRandomBetween(level.levelWidth() / -2 + GameInfo.BOUNDARY_WIDTH, level.levelWidth() / 2 - GameInfo.BOUNDARY_WIDTH);
        int y = Util.getRandomBetween(level.levelHeight() / -2 + GameInfo.BOUNDARY_WIDTH, level.levelHeight() / 2 - GameInfo.BOUNDARY_WIDTH);
        if (Math.abs(x) < distance || Math.abs(y) < distance) {
            getPositionInLevelAwayFromCenter(toLoad, distance, level);
            return;
        }
        toLoad.loadPosition(x, y);
    }

    public static float vectorToAngle(Vector2 angle) {
        return MathUtils.atan2(angle.y, angle.x);
    }
}
