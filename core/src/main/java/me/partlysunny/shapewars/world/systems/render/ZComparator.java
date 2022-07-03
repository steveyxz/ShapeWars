package me.partlysunny.shapewars.world.systems.render;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.render.TextureComponent;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {

    @Override
    public int compare(Entity o1, Entity o2) {
        TextureComponent texture1 = Mappers.textureMapper.get(o1);
        TextureComponent texture2 = Mappers.textureMapper.get(o2);
        return Integer.compare(texture1.zIndex(), texture2.zIndex());
    }
}
