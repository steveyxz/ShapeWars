package me.partlysunny.shapewars.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

    public static int getRandomBetween(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be higher than b");
        }
        if (a == b) {
            return a;
        }
        if (a < 0 && b < 0) {
            return -getRandomBetween(-b, -a);
        }
        if (a < 0) {
            return getRandomBetween(0, -a + b) + a;
        }
        return RAND.nextInt(b - a) + a;
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.set(MathUtils.cos(angle), MathUtils.sin(angle));
        return outVector;
    }

    public static float vectorToAngle(Vector2 angle) {
        return MathUtils.atan2(angle.y, angle.x);
    }
}
