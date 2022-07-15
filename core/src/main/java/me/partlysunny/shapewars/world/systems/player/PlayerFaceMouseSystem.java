package me.partlysunny.shapewars.world.systems.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import me.partlysunny.shapewars.screens.InGameScreen;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.metersToPixels;

public class PlayerFaceMouseSystem extends IteratingSystem {


    public PlayerFaceMouseSystem() {
        super(Family.all(TransformComponent.class, PlayerControlComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);
        float mouseX = Gdx.input.getX();
        float mouseY = GameInfo.SCREEN_HEIGHT - Gdx.input.getY();
        float x = transformComponent.position.x;
        float y = transformComponent.position.y;
        float entityX = metersToPixels(x - InGameScreen.camera.position.x) + GameInfo.SCREEN_WIDTH / 2f;
        float entityY = metersToPixels(y - InGameScreen.camera.position.y) + GameInfo.SCREEN_HEIGHT / 2f;
        float finalY = mouseY - entityY;
        float finalX = mouseX - entityX;
        transformComponent.rotation = MathUtils.atan2(finalY, finalX);
    }
}
