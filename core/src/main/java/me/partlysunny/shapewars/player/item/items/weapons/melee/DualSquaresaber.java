package me.partlysunny.shapewars.player.item.items.weapons.melee;

public class DualSquaresaber extends GenericMeleeWeapon {
    @Override
    public String name() {
        return "Dual Squaresaber";
    }

    @Override
    public String description() {
        return "Luke, I am your father...";
    }

    @Override
    public String texture() {
        return "dualSquaresaber";
    }

    @Override
    public float renderSizeX() {
        return 12;
    }

    @Override
    public int price() {
        return 550;
    }

    @Override
    public float renderSizeY() {
        return 24;
    }

    @Override
    protected int coverAngle() {
        return 120;
    }

    @Override
    protected float life() {
        return 0.8f;
    }

    @Override
    public int damage() {
        return 40;
    }

    @Override
    public float attackDelay() {
        return 0.3f;
    }
}
