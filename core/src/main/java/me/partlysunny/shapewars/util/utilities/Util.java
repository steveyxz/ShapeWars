package me.partlysunny.shapewars.util.utilities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
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
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.render.DeathEffectComponent;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();
    private static final GlyphLayout layout = new GlyphLayout();

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
        if (Mappers.enemyStateMapper.has(a)) {
            enemy = a;
            playerAttack = b;
        }
        if (Mappers.enemyStateMapper.has(b)) {
            if (playerAttack != null) {
                return null;
            }
            enemy = b;
            playerAttack = a;
        }
        if (enemy == null || !Mappers.playerMeleeAttackMapper.has(playerAttack)) {
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
