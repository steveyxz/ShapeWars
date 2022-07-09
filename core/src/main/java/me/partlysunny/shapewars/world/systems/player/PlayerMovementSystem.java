package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.player.PlayerAction;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.world.components.player.PlayerKeyMap;
import me.partlysunny.shapewars.world.components.player.state.State;
import me.partlysunny.shapewars.world.components.player.state.StateComponent;

import static com.badlogic.gdx.Gdx.input;

public class PlayerMovementSystem extends IteratingSystem {


    public PlayerMovementSystem() {
        super(Family.all(PlayerControlComponent.class, RigidBodyComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerControlComponent controller = Mappers.controlMapper.get(entity);
        RigidBodyComponent velocity = Mappers.bodyMapper.get(entity);
        StateComponent state = Mappers.playerStateMapper.get(entity);
        PlayerKeyMap map = controller.keyMap();
        if (map == null) {
            map = new PlayerKeyMap();
        }
        //Movement speed
        float playerSpeed = 0.07f;
        Body body = velocity.rigidBody();
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        Vector2 linearVelocity = body.getLinearVelocity();
        if (input.isKeyPressed(map.getKey(PlayerAction.DOWN)) && linearVelocity.y >= -GameInfo.MAX_VELOCITY) {
            body.applyLinearImpulse(0, -playerSpeed, x, y, true);
            state.setState(State.MOVING);
        }
        if (input.isKeyPressed(map.getKey(PlayerAction.LEFT)) && linearVelocity.x >= -GameInfo.MAX_VELOCITY) {
            body.applyLinearImpulse(-playerSpeed, 0, x, y, true);
            state.setState(State.MOVING);
        }
        if (input.isKeyPressed(map.getKey(PlayerAction.UP)) && linearVelocity.y <= GameInfo.MAX_VELOCITY) {
            body.applyLinearImpulse(0, playerSpeed, x, y, true);
            state.setState(State.MOVING);
        }
        if (input.isKeyPressed(map.getKey(PlayerAction.RIGHT)) && linearVelocity.x <= GameInfo.MAX_VELOCITY) {
            body.applyLinearImpulse(playerSpeed, 0, x, y, true);
            state.setState(State.MOVING);
        }
        if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
            state.setState(State.PASSIVE);
        }
    }
}
