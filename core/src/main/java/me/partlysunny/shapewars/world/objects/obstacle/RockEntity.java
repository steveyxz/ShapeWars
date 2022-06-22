package me.partlysunny.shapewars.world.objects.obstacle;

public class RockEntity extends ObstacleEntity {

    @Override
    protected String getTexture() {
        return "rock";
    }

    @Override
    protected int getWidth() {
        return 10;
    }

    @Override
    protected int getHeight() {
        return 10;
    }

    @Override
    protected int getAngle() {
        return 0;
    }

    @Override
    protected int getHealth() {
        return 40;
    }
}
