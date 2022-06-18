package me.partlysunny.shapewars.world.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable {

    private IntMap<Animation<TextureRegion>> animations = new IntMap<>();

    public IntMap<Animation<TextureRegion>> animations() {
        return animations;
    }

    @Override
    public void reset() {
        animations = new IntMap<>();
    }
}
