package me.partlysunny.shapewars.util.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.PlayerMeleeAttackComponent;

public final class PlayerMeleeAttackFactory {

    private static final PlayerMeleeAttackFactory INSTANCE = new PlayerMeleeAttackFactory();
    //Lower resolution = better shape
    private static final int resolution = 2;

    public static PlayerMeleeAttackFactory getInstance() {
        return INSTANCE;
    }

    public Entity generateMeleeAttack(float rotation, float coverAngle, float radius, int damage) {

        PooledEngine engine = InGameScreen.world.gameWorld();
        Entity playerEntity = InGameScreen.playerInfo.playerEntity();
        TransformComponent playerTransform = Mappers.transformMapper.get(playerEntity);

        Entity attack = engine.createEntity();

        RigidBodyComponent collision = engine.createComponent(RigidBodyComponent.class);
        ChainShape hitShape = new ChainShape();
        int pointCount = (int) (coverAngle / resolution);
        float[] vertices = new float[pointCount * 2];
        for (int i = 0; i < pointCount; i++) {
            float angle = i * resolution - (coverAngle / 2f);
            float x = MathUtils.cos(angle * MathUtils.degreesToRadians) * radius;
            float y = MathUtils.sin(angle * MathUtils.degreesToRadians) * radius;
            vertices[i * 2] = x;
            vertices[i * 2 + 1] = y;
        }
        hitShape.createChain(vertices);
        FixtureDef def = new FixtureDef();
        def.shape = hitShape;
        def.isSensor = true;
        collision.initBody(playerTransform.position.x, playerTransform.position.y, rotation, def, BodyDef.BodyType.KinematicBody, radius);
        attack.add(collision);

        PlayerMeleeAttackComponent meleeAttack = engine.createComponent(PlayerMeleeAttackComponent.class);
        meleeAttack.init(0.2f, attack, rotation, 12, damage);
        attack.add(meleeAttack);

        return attack;
    }

}
