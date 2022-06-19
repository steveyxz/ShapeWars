package me.partlysunny.shapewars.world.components.player.equipment.item.types;

import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.world.components.player.PlayerInfo;
import me.partlysunny.shapewars.world.components.player.equipment.item.Item;

public interface UtilityItem extends Item {

    void use(Vector2 pos, PlayerInfo info);

}
