package me.partlysunny.shapewars.world.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.world.components.TextureComponent;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {

    private static final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);

    @Override
    public int compare(Entity o1, Entity o2) {
        TextureComponent texture1 = textureMapper.get(o1);
        TextureComponent texture2 = textureMapper.get(o2);
        return Integer.compare(texture1.zIndex(), texture2.zIndex());
    }
}
