package me.partlysunny.shapewars.player.item.items.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.bullets.BulletType;
import me.partlysunny.shapewars.bullets.controllers.BulletRestrictions;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Controllers;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.util.factories.BulletFactory;
import me.partlysunny.shapewars.util.factories.ItemFactory;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.world.components.ai.AiDodgeIgnoreComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.BombComponent;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;

public class BombLauncher implements WeaponItem {
    @Override
    public int maxUses() {
        return 10;
    }

    @Override
    public float usesRegenRate() {
        return 2;
    }

    @Override
    public Entity buildEntity(PooledEngine engine) {
        Entity entity = engine.createEntity();
        ItemFactory.getInstance().addItemBasics(engine, entity, this);
        return entity;
    }

    @Override
    public String name() {
        return "Bomb Launcher";
    }

    @Override
    public String description() {
        return "Launches bombs I guess?";
    }

    @Override
    public String texture() {
        return "bombLauncher";
    }

    @Override
    public float renderSizeX() {
        return 6;
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public float renderSizeY() {
        return 6;
    }

    @Override
    public int damage() {
        return 12;
    }

    @Override
    public float attackDelay() {
        return 1;
    }

    @Override
    public void attack(Entity attacker) {
        PooledEngine world = InGameScreen.world.gameWorld();
        Entity basicBomb = world.createEntity();
        TransformComponent transformComponent = world.createComponent(TransformComponent.class);
        TextureComponent textureComponent = world.createComponent(TextureComponent.class);
        RigidBodyComponent rigidBodyComponent = world.createComponent(RigidBodyComponent.class);
        TintComponent tintComponent = world.createComponent(TintComponent.class);
        AiDodgeIgnoreComponent aiIgnore = world.createComponent(AiDodgeIgnoreComponent.class);
        BombComponent bombComponent = world.createComponent(BombComponent.class);

        bombComponent.init(damage());
        textureComponent.init(new TextureRegion(TextureManager.getTexture("basicBomb")));
        transformComponent.init(4, 4);
        TransformComponent shooterPos = Mappers.transformMapper.get(attacker);
        float rotation = shooterPos.rotation;
        float x = MathUtils.cos(rotation) * 5;
        float y = MathUtils.sin(rotation) * 5;

        FixtureDef def = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2, 2);
        def.shape = shape;

        rigidBodyComponent.initBody(shooterPos.position.x + x, shooterPos.position.y + y, shooterPos.rotation, def, BodyDef.BodyType.DynamicBody, 2);
        transformComponent.position.set(shooterPos.position.x + x, shooterPos.position.y + y, 0);

        basicBomb.add(transformComponent);
        basicBomb.add(textureComponent);
        basicBomb.add(rigidBodyComponent);
        basicBomb.add(aiIgnore);
        basicBomb.add(tintComponent);
        basicBomb.add(bombComponent);

        float bulletSpeed = 400;
        x *= (bulletSpeed / 5);
        y *= (bulletSpeed / 5);

        Mappers.bodyMapper.get(basicBomb).rigidBody().applyForceToCenter(x, y, true);
        SoundEffectManager.play("playerShoot", 1);
        InGameScreen.world.gameWorld().addEntity(basicBomb);
        VisualEffectManager.getEffect("explode").playEffect(basicBomb);
    }
}
