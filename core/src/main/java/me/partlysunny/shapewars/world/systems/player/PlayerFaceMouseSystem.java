package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;

public class PlayerFaceMouseSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);

    public PlayerFaceMouseSystem() {
        super(Family.all(TransformComponent.class, PlayerControlComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        float entityX = transformComponent.position.x - InGameScreen.camera.position.x + Gdx.graphics.getWidth() / 2f;
        float entityY = transformComponent.position.y - InGameScreen.camera.position.y + Gdx.graphics.getHeight() / 2f;
        float finalY = mouseY - entityY;
        float finalX = mouseX - entityX;
        transformComponent.rotation = MathUtils.atan2(finalY, finalX);
        //System.out.println("entityX = " + entityX);
        //System.out.println("entityY = " + entityY);
        //System.out.println("mouseX = " + mouseX);
        //System.out.println("mouseY = " + mouseY);
        //System.out.println("finalX = " + finalX);
        //System.out.println("finalY = " + finalY);
        //System.out.println(transformComponent.rotation * MathUtils.radiansToDegrees + 180);
    }
}
