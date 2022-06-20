package me.partlysunny.shapewars.world.objects.obstacle;

import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.world.objects.GameObject;

public interface ObstacleEntity extends GameObject {

    String getTexture();

    float x();

    float y();

    float width();

    float height();

    float angle();

}
