package me.partlysunny.shapewars.world.systems;

import com.badlogic.ashley.core.Entity;
import me.partlysunny.shapewars.world.components.TextureComponent;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        TextureComponent texture1 = o1.getComponent(TextureComponent.class);
        TextureComponent texture2 = o2.getComponent(TextureComponent.class);
        return Integer.compare(texture1.zIndex(), texture2.zIndex());
    }
}
