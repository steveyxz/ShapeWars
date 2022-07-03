package me.partlysunny.shapewars.item.types;

import com.badlogic.gdx.math.Vector2;
import me.partlysunny.shapewars.item.Item;
import me.partlysunny.shapewars.player.PlayerInfo;

public interface UtilityItem extends Item {

    void use(Vector2 pos, PlayerInfo info);

}
