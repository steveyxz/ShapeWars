package me.partlysunny.shapewars.world.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.player.PlayerAction;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.world.components.player.PlayerKeyMap;
import me.partlysunny.shapewars.world.components.player.state.State;
import me.partlysunny.shapewars.world.components.player.state.StateComponent;

import static com.badlogic.gdx.Gdx.input;

public class PlayerMovementSystem extends IteratingSystem {

    private final ComponentMapper<PlayerControlComponent> controllerMapper = ComponentMapper.getFor(PlayerControlComponent.class);
    private final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);
    private final ComponentMapper<StateComponent> stateMapper = ComponentMapper.getFor(StateComponent.class);

    public PlayerMovementSystem() {
        super(Family.all(PlayerControlComponent.class, RigidBodyComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerControlComponent controller = controllerMapper.get(entity);
        RigidBodyComponent velocity = bodyMapper.get(entity);
        StateComponent state = stateMapper.get(entity);
        PlayerKeyMap map = controller.keyMap();
        if (map == null) {
            map = new PlayerKeyMap();
        }
        //Movement speed
        float playerSpeed = 10f;
        Body body = velocity.rigidBody();
        float y = body.getPosition().y;
        float x = body.getPosition().x;
        Vector2 linearVelocity = body.getLinearVelocity();
        if (input.isKeyPressed(map.getKey(PlayerAction.DOWN)) && !(linearVelocity.y < -GameInfo.MAX_VELOCITY)) {
            body.applyForceToCenter(0, -playerSpeed, true);
            state.setState(State.MOVING);
        }
        if (input.isKeyPressed(map.getKey(PlayerAction.LEFT)) && !(linearVelocity.x < -GameInfo.MAX_VELOCITY)) {
            body.applyForceToCenter(-playerSpeed, 0, true);
            state.setState(State.MOVING);
        }
        if (input.isKeyPressed(map.getKey(PlayerAction.UP)) && !(linearVelocity.y > GameInfo.MAX_VELOCITY)) {
            body.applyForceToCenter(0, playerSpeed, true);
            state.setState(State.MOVING);
        }
        if (input.isKeyPressed(map.getKey(PlayerAction.RIGHT)) && !(linearVelocity.x > GameInfo.MAX_VELOCITY)) {
            body.applyForceToCenter(playerSpeed, 0, true);
            state.setState(State.MOVING);
        }
        if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
            state.setState(State.PASSIVE);
        }
    }
}
