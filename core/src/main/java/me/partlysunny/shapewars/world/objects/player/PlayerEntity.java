package me.partlysunny.shapewars.world.objects.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import me.partlysunny.shapewars.TextureManager;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.factories.Box2DFactory;
import me.partlysunny.shapewars.world.components.PlayerCameraFollowComponent;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.world.components.player.PlayerInfo;
import me.partlysunny.shapewars.world.components.player.PlayerTargetComponent;
import me.partlysunny.shapewars.world.components.player.state.StateComponent;
import me.partlysunny.shapewars.world.objects.GameObject;

public class PlayerEntity implements GameObject {
    @Override
    public void createEntity(PooledEngine w) {
        Entity player = w.createEntity();
        int width = 6;
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f);
        shape.setPosition(new Vector2(0, 0));
        FixtureDef def = Box2DFactory.getInstance(InGameScreen.world.physicsWorld()).generateFixture(Box2DFactory.Material.LIGHT, shape);
        //Set components
        RigidBodyComponent rigidBody = w.createComponent(RigidBodyComponent.class);
        rigidBody.initBody(0, 0, 0, def, BodyDef.BodyType.DynamicBody, width / 2f, true);
        player.add(rigidBody);
        TextureComponent texture = w.createComponent(TextureComponent.class);
        texture.init(new TextureRegion(TextureManager.getTexture("player")));
        TransformComponent scale = w.createComponent(TransformComponent.class);
        scale.init(width, width);
        player.add(scale);
        player.add(texture);
        player.add(w.createComponent(PlayerControlComponent.class));
        player.add(w.createComponent(GroundFrictionComponent.class));
        player.add(w.createComponent(PlayerCameraFollowComponent.class));
        player.add(w.createComponent(StateComponent.class));
        player.add(w.createComponent(BulletDeleterComponent.class));
        PlayerTargetComponent playerTarget = w.createComponent(PlayerTargetComponent.class);
        playerTarget.init(rigidBody);
        player.add(playerTarget);
        w.addEntity(player);
        InGameScreen.playerInfo = new PlayerInfo(player);
    }
}
