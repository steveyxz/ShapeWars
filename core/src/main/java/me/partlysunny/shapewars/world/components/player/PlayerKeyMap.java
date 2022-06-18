package me.partlysunny.shapewars.world.components.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerKeyMap {

    private final Map<PlayerAction, Integer> keyMap = new HashMap<>();

    public void setMap(PlayerAction action, Integer key) {
        keyMap.put(action, key);
    }

    public Integer getKey(PlayerAction action) {
        Integer integer = keyMap.get(action);
        if (integer == null) {
            return action.defaultKey();
        }
        return integer;
    }

}
