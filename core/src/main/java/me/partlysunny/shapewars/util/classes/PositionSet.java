package me.partlysunny.shapewars.util.classes;

import com.badlogic.gdx.math.Vector2;
import de.damios.guacamole.gdx.pool.Vector2Pool;

import java.util.ArrayList;
import java.util.List;

public class PositionSet {

    private final Vector2Pool pool = new Vector2Pool(100, 1000);
    private final List<Vector2> positions = new ArrayList<>();

    public int size() {
        return positions.size();
    }

    public void loadPosition(float x, float y) {
        positions.add(pool.obtain().set(x, y));
    }

    public void resetPositions() {
        for (Vector2 v : positions) {
            pool.free(v);
        }
        positions.clear();
    }

    public List<Vector2> positions() {
        return positions;
    }

    @Override
    public String toString() {
        return positions.toString();
    }
}
